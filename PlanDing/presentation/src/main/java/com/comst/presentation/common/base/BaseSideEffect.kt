package com.comst.presentation.common.base

// 그외 상태 변화가 아닌 부수 효과
interface BaseSideEffect{
    data class ShowError(val message: String?) : BaseSideEffect

}