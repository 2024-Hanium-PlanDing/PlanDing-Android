package com.comst.presentation.common.base

// 그외 상태 변화가 아닌 부수 효과
interface BaseSideEffect{
    data class ShowToast(val message: String?) : BaseSideEffect
    object NavigateToLogin: BaseSideEffect
}