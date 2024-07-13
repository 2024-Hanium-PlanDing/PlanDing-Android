package com.comst.presentation.main.group.detail

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.group.GetGroupInformationUseCase
import com.comst.domain.util.DateUtils
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.GroupDetailContract.*
import com.comst.presentation.model.group.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val getGroupInformationUseCase: GetGroupInformationUseCase,
) : BaseViewModel<GroupDetailUIState, GroupDetailSideEffect, GroupDetailIntent, GroupDetailEvent>(GroupDetailUIState()) {

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
        getGroupInformationUseCase(groupCode).onSuccess {
            setState {
                copy(
                    groupProfile =  it.toUIModel()
                )
            }
        }.onFailure {

        }
        setState { copy(isLoading = false) }
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
            copy(selectLocalDate = selectedLocalDate)
        }
        setEvent(GroupDetailEvent.DateSelected(selectedLocalDate))
    }

    private fun onDateSelected(date: LocalDate) = viewModelScope.launch {
        val newSelectLocalDate = date
        val weeklySchedulePeriod = DateUtils.getWeekStartAndEnd(newSelectLocalDate)
        val dailyPeriod = DateUtils.getDayStartAndEnd(newSelectLocalDate)

    }

    override fun handleError(exception: Exception) {
        super.handleError(exception)
        setToastEffect(exception.message.orEmpty())
    }
}