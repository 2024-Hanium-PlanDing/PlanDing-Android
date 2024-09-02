package com.comst.presentation.main.group.detail.scheduleDetail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.model.base.Schedule
import com.comst.domain.usecase.groupTask.GetTaskListOfScheduleUseCase
import com.comst.domain.usecase.local.GetTokenUseCase
import com.comst.domain.usecase.local.GetUserCodeUseCase
import com.comst.domain.util.onError
import com.comst.domain.util.onException
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.BuildConfig
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.common.util.UniqueList
import com.comst.presentation.main.group.detail.GroupDetailViewModel
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailEvent
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailIntent
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailSideEffect
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.ScheduleDetailUIState
import com.comst.presentation.model.group.socket.ReceiveScheduleDTO
import com.comst.presentation.model.group.socket.WebSocketAction
import com.comst.presentation.model.group.socket.WebSocketResponse
import com.comst.presentation.model.group.toTaskUIModel
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
import org.hildan.krossbow.stomp.conversions.moshi.withMoshi
import org.hildan.krossbow.stomp.frame.StompFrame
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import javax.inject.Inject

private const val TAG = "소켓"

@HiltViewModel
class ScheduleDetailViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getUserCodeUseCase: GetUserCodeUseCase,
    private val getTaskListOfScheduleUseCase: GetTaskListOfScheduleUseCase
) : BaseViewModel<ScheduleDetailUIState, ScheduleDetailSideEffect, ScheduleDetailIntent, ScheduleDetailEvent>(
    ScheduleDetailUIState()
) {

    private lateinit var token: String

    private lateinit var stompSession: StompSession
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private lateinit var newTask: Flow<StompFrame.Message>
    override fun handleIntent(intent: ScheduleDetailIntent) {
        when (intent) {
            is ScheduleDetailIntent.Initialize -> initialize(
                groupCode = intent.groupCode,
                schedule = intent.schedule
            )

            is ScheduleDetailIntent.SelectTaskStatusOption -> onSelectTaskStatusOption(intent.option)
        }
    }

    override fun handleEvent(event: ScheduleDetailEvent) {

    }

    fun initialize(groupCode: String, schedule: Schedule) = viewModelScope.launch {
        setState {
            copy(
                groupCode = groupCode,
                schedule = schedule
            )
        }

        val tokenDeferred = async { getTokenUseCase() }
        val userCodeDeferred = async { getUserCodeUseCase() }
        val taskListDeferred = async {
            getTaskListOfScheduleUseCase(
                groupCode = currentState.groupCode,
                scheduleId = schedule.scheduleId
            )
        }

        val tokenResult = tokenDeferred.await()
        val userCodeResult = userCodeDeferred.await()
        val taskListResult = taskListDeferred.await()

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

        taskListResult.onSuccess { groupTaskResponseModel ->
            val tasks = groupTaskResponseModel.task.map { it.toTaskUIModel() }
            setState {
                copy(
                    taskOriginalList = UniqueList({ it.id }, tasks)
                )
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
            Log.d("테스크","실패")
        }
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
                BuildConfig.STOMP_ENDPOINT,
                customStompConnectHeaders = createStompHeaders()
            ).withMoshi(moshi)

            Log.d(TAG, "Connected to STOMP, subscribing to channels...")

            subscribeToChannels()

            Log.d(TAG, "Subscribed to channels, starting to collect messages...")

            launch { collectTaskMessages() }
        } catch (e: Exception) {
            Log.e(TAG, "Error connecting to STOMP", e)
        }
    }

    private fun createStompHeaders(): Map<String, String> {
        return mapOf(
            GroupDetailViewModel.HEADER_AUTHORIZATION to token,
            GroupDetailViewModel.HEADER_GROUP_CODE to currentState.groupCode
        )
    }

    private suspend fun subscribeToChannels() {
        newTask = stompSession.subscribe(
            StompSubscribeHeaders(
                destination = "${GroupDetailViewModel.SUBSCRIBE_SCHEDULE_URL}${currentState.groupCode}",
                customHeaders = createStompHeaders()
            )
        )
    }

    private suspend fun collectTaskMessages() {
        newTask.collect { message ->
            try {
                val taskJson = message.bodyAsText
                Log.d(TAG, "Received schedule: $taskJson")

                val type = Types.newParameterizedType(
                    WebSocketResponse::class.java,
                    ReceiveScheduleDTO::class.java
                )
                val adapter = moshi.adapter<WebSocketResponse<ReceiveScheduleDTO>>(type)
                val response = adapter.fromJson(taskJson)

                if (response != null) {
                    handleReceiveTask(response)
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

    private fun handleReceiveTask(response: WebSocketResponse<ReceiveScheduleDTO>) {
        if (response.data != null) {

            when (response.data.action) {
                WebSocketAction.CREATE.name -> {

                }

                WebSocketAction.UPDATE.name -> {

                }

                WebSocketAction.DELETE.name -> {

                }
            }
        } else {
            Log.e(TAG, "Received error response: ${response.errorResponse}")
        }
    }


    private fun onSelectTaskStatusOption(option: ScheduleDetailContract.TaskStatus) {
        setState {
            copy(
                selectedOption = option
            )
        }
    }

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_GROUP_CODE = "groupCode"

        const val SEND_TASK_CREATE_URL = "/pub/planner/create/"
        const val SEND_TASK_UPDATE_URL = "/pub/planner/update/"
        const val SEND_TASK_DELETE_URL = "/pub/planner/delete/"

        const val SUBSCRIBE_TASK_URL = "/sub/planner/"
    }
}