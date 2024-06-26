package com.comst.presentation.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comst.domain.util.Resources
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

//https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d
abstract class BaseViewModel<S : UIState, A : UISideEffect, E : UIEvent>(
    initialState: S,
) : ViewModel() {

    private val _uiState = MutableStateFlow<S>(initialState)
    val uiState = _uiState.asStateFlow()

    /**
     * `Channel` replicate SingleLiveEvent behavior.
     */
    private val _effect: Channel<A> = Channel()
    val effect = _effect.receiveAsFlow()

    // Get current state
    protected val currentState: S
        get() = _uiState.value

    open fun setEvent(event: E) {
        dispatchEvent(event)
    }

    private fun dispatchEvent(event: E) = viewModelScope.launch {
        handleEvent(event)
    }

    protected abstract suspend fun handleEvent(event: E)

    protected fun setState(reduce: S.() -> S) {
        val state = currentState.reduce()
        _uiState.value = state
    }

    protected fun setEffect(vararg builder: A) {
        for (effectValue in builder) {
            viewModelScope.launch { _effect.send(effectValue) }
        }
    }

    protected suspend fun <T> Flow<Resources<T>>.collectWithCallback(
        onSuccess: suspend (T) -> Unit,
        onFailed: suspend (Throwable) -> Unit,
    ) {
        collect { result ->
            when (result) {
                is Resources.Success -> {
                    onSuccess.invoke(result.data)
                }

                is Resources.Failed -> {
                    onFailed.invoke(result.throwable)
                }
            }
        }
    }
}