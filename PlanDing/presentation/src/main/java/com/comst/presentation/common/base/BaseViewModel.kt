package com.comst.presentation.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comst.domain.util.AccountNotFoundException
import com.comst.domain.util.BadRequestException
import com.comst.domain.util.InternalServerErrorException
import com.comst.domain.util.Resources
import com.comst.domain.util.ServerNotFoundException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : UIState, A : BaseSideEffect, I : BaseIntent, E : BaseEvent>(
    initialState: S,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _effect: Channel<A> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _intent: MutableSharedFlow<I> = MutableSharedFlow()
    private val intent = _intent.asSharedFlow()

    private val _event: MutableSharedFlow<E> = MutableSharedFlow()
    private val event = _event.asSharedFlow()

    protected val apiExceptionHandler = CoroutineExceptionHandler { _, exception ->
        apiHandleException(exception)
    }

    init {
        viewModelScope.launch {
            intent.collect { intent ->
                handleIntent(intent)
            }
        }

        viewModelScope.launch {
            event.collect { event ->
                handleEvent(event)
            }
        }
    }

    protected val currentState: S
        get() = _uiState.value

    open fun setIntent(intent: I) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    open fun setEvent(event: E) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected abstract fun handleIntent(intent: I)

    protected abstract fun handleEvent(event: E)

    protected fun setToastEffect(message: String) {
        viewModelScope.launch {
            setEffect(BaseSideEffect.ShowToast(message) as A)
        }
    }

    protected fun setState(reduce: S.() -> S) {
        val state = currentState.reduce()
        _uiState.value = state
    }

    protected fun setEffect(vararg builders: A) {
        for (effect in builders) {
            viewModelScope.launch { _effect.send(effect) }
        }
    }

    protected suspend fun <T> Flow<Resources<T>>.collectWithCallback(
        onSuccess: suspend (T) -> Unit,
        onFailed: suspend (Throwable) -> Unit,
    ) {
        collect { result ->
            when (result) {
                is Resources.Success -> onSuccess(result.data)
                is Resources.Failed -> onFailed(result.throwable)
            }
        }
    }

    private fun apiHandleException(exception: Throwable) {
        when (exception) {
            is BadRequestException -> setToastEffect("잘못된 요청입니다.")
            is AccountNotFoundException -> setToastEffect("계정을 찾을 수 없습니다.")
            is ServerNotFoundException -> setToastEffect("서버를 찾을 수 없습니다.")
            is InternalServerErrorException -> setToastEffect("서버에 문제가 발생했습니다. 나중에 다시 시도해 주세요.")
            else -> setToastEffect("알 수 없는 오류가 발생했습니다.")
        }
    }
}