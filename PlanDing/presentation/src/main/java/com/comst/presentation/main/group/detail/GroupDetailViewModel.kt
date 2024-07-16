package com.comst.presentation.main.group.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.model.base.Schedule
import com.comst.domain.usecase.group.GetGroupInformationUseCase
import com.comst.domain.usecase.groupSchedule.GetGroupScheduleUseCase
import com.comst.domain.usecase.local.GetTokenUseCase
import com.comst.domain.usecase.local.GetUserCodeUseCase
import com.comst.domain.util.DateUtils
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.BuildConfig.STOMP_ENDPOINT
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.GroupDetailContract.*
import com.comst.presentation.model.group.socket.ReceiveScheduleDTO
import com.comst.presentation.model.group.socket.SendScheduleDTO
import com.comst.presentation.model.group.socket.WebSocketResponse
import com.comst.presentation.model.group.socket.toDomainModel
import com.comst.presentation.model.group.toUIModel
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.conversions.convertAndSend
import org.hildan.krossbow.stomp.conversions.moshi.withMoshi
import org.hildan.krossbow.stomp.frame.StompFrame
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

private const val TAG = "소켓"
@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getUserCodeUseCase: GetUserCodeUseCase,
    private val getGroupInformationUseCase: GetGroupInformationUseCase,
    private val getGroupScheduleUseCase: GetGroupScheduleUseCase
) : BaseViewModel<GroupDetailUIState, GroupDetailSideEffect, GroupDetailIntent, GroupDetailEvent>(GroupDetailUIState()) {

    private lateinit var token: String
    private lateinit var userCode: String

    private lateinit var stompSession: StompSession
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private lateinit var newSchedule: Flow<StompFrame.Message>

    override fun handleIntent(intent: GroupDetailIntent) {
        when(intent){
            is GroupDetailIntent.OpenBottomSheetClick -> onOpenBottomSheet()
            is GroupDetailIntent.CloseBottomSheetClick -> onCloseBottomSheet()
            is GroupDetailIntent.SelectDate -> onSelectDate(intent.date)
        }
    }

    override fun handleEvent(event: GroupDetailEvent) {
        when (event){
            is GroupDetailEvent.DateSelected -> onDateSelected(event.date)
        }
    }

    fun initialize(groupCode: String) = viewModelScope.launch {
        setState { copy(isLoading = true) }

        val tokenDeferred = async { getTokenUseCase() }
        val userCodeDeferred = async { getUserCodeUseCase() }
        val groupInfoDeferred = async { getGroupInformationUseCase(groupCode) }
        val groupScheduleDeferred = async {
            getGroupScheduleUseCase(
                groupCode = groupCode,
                schedulePeriodModel = DateUtils.getWeekStartAndEnd(currentState.selectLocalDate)
            )
        }

        val tokenResult = tokenDeferred.await()
        val userCodeResult = userCodeDeferred.await()
        val groupInfoResult = groupInfoDeferred.await()
        val groupScheduleResult = groupScheduleDeferred.await()

        var isSuccess = true

        tokenResult.onSuccess {
            it?.let { token = "Bearer $it" }
        }.onFailure {
            isSuccess = false
        }

        userCodeResult.onSuccess {
            it?.let { userCode = it }
        }.onFailure {
            isSuccess = false
        }


        groupInfoResult.onSuccess { groupInformation ->
            setState {
                copy(groupProfile = groupInformation.toUIModel())
            }
        }.onFailure {
            isSuccess = false
        }

        groupScheduleResult.onSuccess { groupSchedules ->
            setState {
                copy(selectWeekGroupScheduleEvents = groupSchedules)
            }
        }.onFailure {
            isSuccess = false
        }


        if (isSuccess) {
            connectStomp()
        }else{
            // 처리
        }

        setState { copy(isLoading = false) }
    }

    private fun connectStomp() = viewModelScope.launch {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

        val client = StompClient(
            OkHttpWebSocketClient(okHttpClient)
        )

        try {
            stompSession = client.connect(
                STOMP_ENDPOINT,
                customStompConnectHeaders = mapOf(
                    HEADER_AUTHORIZATION to token,
                    HEADER_GROUP_CODE to currentState.groupProfile.groupCode
                )
            ).withMoshi(moshi)

            newSchedule = stompSession.subscribe(
                StompSubscribeHeaders(
                    destination = "$SUBSCRIBE_URL${currentState.groupProfile.groupCode}",
                    customHeaders = mapOf(
                        HEADER_AUTHORIZATION to token,
                        HEADER_GROUP_CODE to currentState.groupProfile.groupCode
                    )
                )
            )

            newSchedule.collect {
                try {
                    val scheduleJson = it.bodyAsText
                    Log.d(TAG, "Received message: $scheduleJson")

                    val moshi = Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()

                    val type = Types.newParameterizedType(WebSocketResponse::class.java, ReceiveScheduleDTO::class.java)
                    val adapter = moshi.adapter<WebSocketResponse<ReceiveScheduleDTO>>(type)
                    val response = adapter.fromJson(scheduleJson)

                    if (response?.success == true && response.data != null) {
                        val schedule = response.data
                        Log.d(TAG, "Parsed schedule: $schedule")
                        setState {
                            copy(
                                newScheduleEvents = newScheduleEvents + schedule.toDomainModel()
                            )
                        }
                    } else {
                        Log.e(TAG, "Received error response: ${response?.errorResponse}")
                    }
                } catch (e: JsonDataException) {
                    Log.e(TAG, "JSON parsing error", e)
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing message", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error connecting to STOMP", e)
        }
    }
    fun updateSchedule(message: String) {

    }

    fun postSchedule() {
        viewModelScope.launch {
            try {
                val fullUrl = "$SEND_URL${currentState.groupProfile.groupCode}"
                Log.d(TAG, fullUrl)

                val headers = StompSendHeaders(
                    destination = fullUrl,
                    customHeaders = mapOf(
                        HEADER_AUTHORIZATION to token,
                        HEADER_GROUP_CODE to currentState.groupProfile.groupCode
                    )
                )

                val schedule = SendScheduleDTO(
                    groupCode = currentState.groupProfile.groupCode,
                    scheduleId = null,
                    userCode = userCode,
                    title = "학교놀러와",
                    content = "학교놀러와",
                    scheduleDate = "2024-09-03",
                    startTime = 9,
                    endTime = 13,
                    action = "CREATE"
                )

                stompSession.withMoshi(moshi).convertAndSend(
                    headers = headers,
                    body = schedule
                )
                Log.d(TAG, "Message sent successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error sending message", e)
            }
        }
    }
    fun cancelStomp() {
        try {
            viewModelScope.launch {
                stompSession.disconnect()
            }
        } catch (e: Exception) {
            Log.d(TAG, "cancelStomp: ${e.message}")
        }
    }

    private fun onOpenBottomSheet() {
        setState {
            copy(isBottomSheetVisible = true)
        }
    }

    private fun onCloseBottomSheet() {
        setState {
            copy(isBottomSheetVisible = false)
        }
    }

    private fun onSelectDate(date: Date) {
        val selectedLocalDate = DateUtils.dateToLocalDate(date)
        setState {
            copy(
                selectLocalDate = selectedLocalDate,
                isBottomSheetVisible = false
            )
        }
        setEvent(GroupDetailEvent.DateSelected(selectedLocalDate))
    }

    private fun onDateSelected(date: LocalDate) = viewModelScope.launch {
        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(date)
        val dailyPeriod = DateUtils.getDayStartAndEnd(date)

        getGroupScheduleUseCase(
            groupCode = currentState.groupProfile.groupCode,
            schedulePeriodModel = weeklySchedulePeriod
        ).onSuccess {
            setState {
                copy(
                    selectLocalDate = date,
                    selectUIDate = DateUtils.localDateToUIDate(date),
                    selectDay = DateUtils.getDayOfWeek(date),
                    selectedWeekdays = DateUtils.getWeekDays(date),
                    selectWeekGroupScheduleEvents = it,
                    newScheduleEvents = emptyList()
                )
            }
        }.onFailure {

        }


    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setToastEffect(exception.message.orEmpty())
    }

    companion object{
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_GROUP_CODE = "groupCode"

        const val SEND_URL = "/pub/schedule/"
        const val SUBSCRIBE_URL = "/sub/schedule/"
    }
}