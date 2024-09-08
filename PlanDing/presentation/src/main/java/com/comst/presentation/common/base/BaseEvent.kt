package com.comst.presentation.common.base

// 시스템 내외부 발생 - 통신 오류로 에러 메시지 출력
interface BaseEvent {
    data class AccountNotFound(val message: String? = null) : BaseEvent
    data class ServerNotFound(val message: String? = null) : BaseEvent
    data class InternalServerError(val message: String? = null) : BaseEvent
    data class BadRequest(val message: String? = null) : BaseEvent
    object ReAuthenticationRequired : BaseEvent
}