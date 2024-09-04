package com.comst.presentation.main.group.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.model.base.Schedule
import com.comst.domain.usecase.chat.GetChatMessageListUseCase
import com.comst.domain.usecase.chat.PostChatMessageUseCase
import com.comst.domain.usecase.group.GetGroupInformationUseCase
import com.comst.domain.usecase.groupSchedule.GetGroupScheduleUseCase
import com.comst.domain.usecase.local.GetTokenUseCase
import com.comst.domain.usecase.local.GetUserCodeUseCase
import com.comst.domain.util.DateUtils
import com.comst.domain.util.onException
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.BuildConfig.STOMP_ENDPOINT
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.common.util.UniqueList
import com.comst.presentation.main.group.detail.GroupDetailContract.GroupDetailEvent
import com.comst.presentation.main.group.detail.GroupDetailContract.GroupDetailIntent
import com.comst.presentation.main.group.detail.GroupDetailContract.GroupDetailSideEffect
import com.comst.presentation.main.group.detail.GroupDetailContract.GroupDetailUIState
import com.comst.presentation.model.group.socket.ReceiveChatDTO
import com.comst.presentation.model.group.socket.ReceiveScheduleDTO
import com.comst.presentation.model.group.socket.SendCreateScheduleDTO
import com.comst.presentation.model.group.socket.WebSocketAction
import com.comst.presentation.model.group.socket.WebSocketResponse
import com.comst.presentation.model.group.socket.toDomainModel
import com.comst.presentation.model.group.toGroupProfileUIModel
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

private const val TAG = "그룹 디테일 소켓"
@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getUserCodeUseCase: GetUserCodeUseCase,
    private val getGroupInformationUseCase: GetGroupInformationUseCase,
    private val getGroupScheduleUseCase: GetGroupScheduleUseCase,
    private val getChatMessageListUseCase: GetChatMessageListUseCase,
    private val postChatMessageUseCase: PostChatMessageUseCase
) : BaseViewModel<GroupDetailUIState, GroupDetailSideEffect, GroupDetailIntent, GroupDetailEvent>(GroupDetailUIState()) {

    private lateinit var token: String

    private lateinit var stompSession: StompSession
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private lateinit var newSchedule: Flow<StompFrame.Message>
    private lateinit var newChat: Flow<StompFrame.Message>

    override fun handleIntent(intent: GroupDetailIntent) {
        when(intent){
            is GroupDetailIntent.OpenCalendarBottomSheet -> onOpenCalendarBottomSheet()
            is GroupDetailIntent.CloseCalendarBottomSheet -> onCloseCalendarBottomSheet()
            is GroupDetailIntent.SelectDate -> onSelectDate(intent.date)
            is GroupDetailIntent.ToggleView -> onToggleView()
            is GroupDetailIntent.SelectDay -> onSelectDay(intent.index)
            is GroupDetailIntent.ShowAddScheduleDialog -> onShowAddScheduleDialog()
            is GroupDetailIntent.HideAddScheduleDialog -> onHideAddScheduleDialog()
            is GroupDetailIntent.ShowAddGroupMemberDialog -> onShowAddGroupMemberDialog()
            is GroupDetailIntent.HideAddGroupMemberDialog -> onHideAddGroupMemberDialog()
            is GroupDetailIntent.ChangePage -> onChangePage(intent.pageIndex)
            is GroupDetailIntent.SendChat -> onSendChat()
            is GroupDetailIntent.ChatChange -> onChatChange(intent.chat)
            is GroupDetailIntent.CreateSchedule -> onCreateSchedule(intent.newSchedule)
            is GroupDetailIntent.OpenScheduleDetailBottomSheet -> onOpenScheduleDetailBottomSheet(intent.schedule)
            is GroupDetailIntent.CloseScheduleDetailBottomSheet -> onCloseScheduleDetailBottomSheet()
        }
    }

    override fun handleEvent(event: GroupDetailEvent) {
        when (event){
            is GroupDetailEvent.DateSelected -> onDateSelected(event.date)
            is GroupDetailEvent.AddGroupSchedule -> onAddGroupSchedule(event.schedule)
        }
    }

    fun initialize(groupCode: String) = viewModelScope.launch(apiExceptionHandler) {
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
        val groupChatMessageDeferred = async { getChatMessageListUseCase(groupCode) }

        val tokenResult = tokenDeferred.await()
        val userCodeResult = userCodeDeferred.await()
        val groupInfoResult = groupInfoDeferred.await()
        val groupScheduleResult = groupScheduleDeferred.await()
        val groupChatMessageResult = groupChatMessageDeferred.await()

        var isSuccess = true

        tokenResult.onSuccess {
            it?.let { token = "Bearer $it" }
        }.onFailure {
            isSuccess = false
        }

        userCodeResult.onSuccess {
            it?.let {  userCode ->
                setState {
                    copy(
                        userCode = userCode
                    )
                }
            }
        }.onFailure {
            isSuccess = false
        }


        groupInfoResult.onSuccess { groupInformation ->
            setState {
                copy(
                    groupProfile = groupInformation.toGroupProfileUIModel(),
                    groupMember = groupInformation.users
                )
            }
        }.onFailure {
            isSuccess = false
        }.onException { exception ->
            isSuccess = false
            throw exception
        }

        groupScheduleResult.onSuccess { scheduleList ->
            setState {
                copy(selectWeekGroupScheduleOriginalList = UniqueList({ it.scheduleId }, scheduleList.map { it.scheduleCommonResponse }))
            }
        }.onFailure {
            isSuccess = false
        }.onException { exception ->
            isSuccess = false
            throw exception
        }

        groupChatMessageResult.onSuccess { chatList ->
            setState {
                copy(chatOriginalList = UniqueList({it.id}, chatList))
            }
        }.onFailure {
            isSuccess = false
        }.onException { exception ->
            isSuccess = false
            throw exception
        }

        if (isSuccess) {
            connectStomp()
        }else{
            // 처리
            Log.d("챗","실패")
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
            Log.d(TAG, "Connecting to STOMP...")
            stompSession = client.connect(
                STOMP_ENDPOINT,
                customStompConnectHeaders = createStompHeaders()
            ).withMoshi(moshi)

            Log.d(TAG, "Connected to STOMP, subscribing to channels...")

            subscribeToChannels()

            Log.d(TAG, "Subscribed to channels, starting to collect messages...")

            launch { collectScheduleMessages() }
            launch { collectChatMessages() }
        } catch (e: Exception) {
            Log.e(TAG, "Error connecting to STOMP", e)
        }
    }

    private fun createStompHeaders(): Map<String, String> {
        return mapOf(
            HEADER_AUTHORIZATION to token,
            HEADER_GROUP_CODE to currentState.groupProfile.groupCode
        )
    }

    private suspend fun subscribeToChannels() {
        newSchedule = stompSession.subscribe(
            StompSubscribeHeaders(
                destination = "$SUBSCRIBE_SCHEDULE_URL${currentState.groupProfile.groupCode}",
                customHeaders = createStompHeaders()
            )
        )

        newChat = stompSession.subscribe(
            StompSubscribeHeaders(
                destination = "$SUBSCRIBE_CHAT_URL${currentState.groupProfile.groupCode}",
                customHeaders = createStompHeaders()
            )
        )
    }

    private suspend fun collectScheduleMessages() {
        newSchedule.collect { message ->
            try {
                val scheduleJson = message.bodyAsText
                Log.d(TAG, "Received schedule: $scheduleJson")

                val type = Types.newParameterizedType(WebSocketResponse::class.java, ReceiveScheduleDTO::class.java)
                val adapter = moshi.adapter<WebSocketResponse<ReceiveScheduleDTO>>(type)
                val response = adapter.fromJson(scheduleJson)

                if (response != null) {
                    handleReceiveSchedule(response)
                } else {
                    Log.e(TAG, "Failed to parse response")
                }
            } catch (e: JsonDataException) {
                Log.e(TAG, "JSON parsing error", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error processing message", e)
            }
        }
    }

    private suspend fun collectChatMessages() {
        newChat.collect { message ->
            try {
                val chatJson = message.bodyAsText
                Log.d(TAG, "Received chat: $chatJson")

                val type = Types.newParameterizedType(WebSocketResponse::class.java, ReceiveChatDTO::class.java)
                val adapter = moshi.adapter<WebSocketResponse<ReceiveChatDTO>>(type)
                val response = adapter.fromJson(chatJson)

                if (response != null) {
                    handleReceiveChat(response)
                } else {
                    Log.e(TAG, "Failed to parse response")
                }
            } catch (e: JsonDataException) {
                Log.e(TAG, "JSON parsing error", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error processing message", e)
            }
        }
    }


    fun updateSchedule(message: String) {

    }

    private fun onCreateSchedule(sendCreateScheduleDTO: SendCreateScheduleDTO) {
        viewModelScope.launch {
            try {
                val fullUrl = "$SEND_SCHEDULE_CREATE_URL${currentState.groupProfile.groupCode}"
                Log.d(TAG, fullUrl)

                val headers = StompSendHeaders(
                    destination = fullUrl,
                    customHeaders = createStompHeaders()
                )
                stompSession.withMoshi(moshi).convertAndSend(
                    headers = headers,
                    body = sendCreateScheduleDTO
                )
                Log.d(TAG, "Message sent successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error sending message", e)
            }
        }
    }

    private fun onSendChat() = viewModelScope.launch(apiExceptionHandler){
        if (!canHandleClick(SEND_CHAT)) return@launch

        postChatMessageUseCase(
            groupCode = currentState.groupProfile.groupCode,
            content = currentState.chat
        ).onSuccess {
            setState {
                copy(
                    chat = ""
                )
            }
        }.onFailure {

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

    private fun handleReceiveSchedule(response: WebSocketResponse<ReceiveScheduleDTO>) {
        if (response.data != null) {
            val newSchedule = response.data.toDomainModel()
            Log.d(TAG, "Parsed schedule: $newSchedule")

            when (response.data.action) {
                WebSocketAction.CREATE.name -> {
                    setState {
                        copy(
                            newScheduleList = newScheduleList.addOrUpdate(newSchedule)
                        )
                    }
                }
                WebSocketAction.UPDATE.name -> {
                    if (currentState.selectWeekGroupScheduleOriginalList.contains(newSchedule)) {
                        setState {
                            copy(
                                selectWeekGroupScheduleOriginalList = selectWeekGroupScheduleOriginalList.addOrUpdate(newSchedule)
                            )
                        }
                    } else {
                        setState {
                            copy(
                                newScheduleList = newScheduleList.addOrUpdate(newSchedule)
                            )
                        }
                    }
                }
                WebSocketAction.DELETE.name -> {
                    if (currentState.selectWeekGroupScheduleOriginalList.contains(newSchedule)) {
                        setState {
                            copy(
                                selectWeekGroupScheduleOriginalList = selectWeekGroupScheduleOriginalList.remove(newSchedule)
                            )
                        }
                    } else {
                        setState {
                            copy(
                                newScheduleList = newScheduleList.remove(newSchedule)
                            )
                        }
                    }
                }
            }
        } else {
            Log.e(TAG, "Received error response: ${response.errorResponse}")
        }
    }

    private fun handleReceiveChat(response: WebSocketResponse<ReceiveChatDTO>){
        if (response.data != null){
            val newChat = response.data.toDomainModel()
            setState {
                copy(
                    newChatList = newChatList.addOrUpdate(newChat)
                )
            }
        }
    }

    private fun onOpenCalendarBottomSheet() {
        setState {
            copy(isCalendarBottomSheetVisible = true)
        }
    }

    private fun onCloseCalendarBottomSheet() {
        setState {
            copy(isCalendarBottomSheetVisible = false)
        }
    }

    private fun onSelectDate(date: Date) {
        if (!canHandleClick(SELECT_DATE)) return

        val selectedLocalDate = DateUtils.dateToLocalDate(date)
        setState {
            copy(
                selectLocalDate = selectedLocalDate,
                isCalendarBottomSheetVisible = false
            )
        }
        setEvent(GroupDetailEvent.DateSelected(selectedLocalDate))
    }
    private fun onDateSelected(date: LocalDate) = viewModelScope.launch(apiExceptionHandler) {
        val newSelectedWeekdays = DateUtils.getWeekDays(date)
        val selectUIDate = DateUtils.localDateToUIDate(date)
        val selectDay = DateUtils.getDayOfWeek(date)
        // 현재 선택된 요일 목록과 새로운 요일 목록을 값으로 비교
        if (currentState.selectedWeekdays != newSelectedWeekdays) {
            val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(date)

            getGroupScheduleUseCase(
                groupCode = currentState.groupProfile.groupCode,
                schedulePeriodModel = weeklySchedulePeriod
            ).onSuccess { scheduleList ->
                setState {
                    copy(
                        selectLocalDate = date,
                        selectUIDate = selectUIDate,
                        selectDay = selectDay,
                        selectedWeekdays = newSelectedWeekdays,
                        selectWeekGroupScheduleOriginalList = UniqueList({ it.scheduleId }, scheduleList.map { it.scheduleCommonResponse }),
                        newScheduleList = UniqueList({ it.scheduleId }),
                        selectedDayIndex = newSelectedWeekdays.indexOfFirst {
                            it.firstOrNull() == selectDay.firstOrNull()
                        }
                    )
                }
            }.onFailure {
                // 실패 처리 로직 추가 가능
            }.onException { exception ->
                throw exception
            }
        } else {
            // 동일한 주라면 날짜와 요일만 업데이트
            setState {
                copy(
                    selectLocalDate = date,
                    selectUIDate = selectUIDate,
                    selectDay = selectDay,
                    selectedDayIndex = newSelectedWeekdays.indexOfFirst {
                        it.firstOrNull() == selectDay.firstOrNull()
                    }
                )
            }
        }
    }

    private fun onToggleView(){
        setState {
            copy(
                isBarChartView = !isBarChartView
            )
        }
    }

    private fun onSelectDay(index: Int){
        val newSelectedLocalDate = DateUtils.getDateFromWeekdayIndex(currentState.selectLocalDate, index)

        setState {
            copy(
                selectUIDate = DateUtils.localDateToUIDate(newSelectedLocalDate),
                selectDay = DateUtils.getDayOfWeek(newSelectedLocalDate),
                selectedDayIndex = index
            )
        }
    }

    private fun onShowAddScheduleDialog() {
        setState {
            copy(isAddScheduleDialogVisible = true)
        }
    }

    private fun onHideAddScheduleDialog() {
        setState {
            copy(isAddScheduleDialogVisible = false)
        }
    }

    private fun onShowAddGroupMemberDialog() {
        setState {
            copy(isAddGroupMemberDialogVisible = true)
        }
    }

    private fun onHideAddGroupMemberDialog() {
        setState {
            copy(isAddGroupMemberDialogVisible = false)
        }
    }

    private fun onAddGroupSchedule(schedule: Schedule) {
        setState {
            copy(

            )
        }
    }

    private fun onOpenScheduleDetailBottomSheet(schedule: Schedule) {
        setState {
            copy(
                selectSchedule = schedule,
                isScheduleDetailDialogVisible = true
            )
        }
    }

    private fun onCloseScheduleDetailBottomSheet() {
        setState {
            copy(isScheduleDetailDialogVisible = false)
        }
    }

    private fun onChangePage(pageIndex: Int) {
        setState {
            copy(
                currentPage = pageIndex
            )
        }
    }

    private fun onChatChange(chat: String){
        setState {
            copy(chat = chat)
        }
    }

    companion object{
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_GROUP_CODE = "groupCode"

        const val SEND_SCHEDULE_CREATE_URL = "/pub/schedule/create/"
        const val SEND_SCHEDULE_UPDATE_URL = "/pub/schedule/update/"
        const val SEND_SCHEDULE_DELETE_URL = "/pub/schedule/delete/"
        const val SEND_CHAT_URL = "/pub/chat/"

        const val SUBSCRIBE_SCHEDULE_URL = "/sub/schedule/"
        const val SUBSCRIBE_CHAT_URL = "/sub/chat/"

        const val SEND_CHAT = "sendChatClick"
        const val SELECT_DATE = "selectDate"
    }
}