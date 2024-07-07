package com.comst.presentation.main.group.detail

import android.util.Log
import com.comst.presentation.common.base.BaseViewModel
import com.comst.presentation.main.group.detail.GroupDetailContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(

): BaseViewModel<GroupDetailUIState, GroupDetailUISideEffect, GroupDetailUIEvent>(GroupDetailUIState()){

    override suspend fun handleEvent(event: GroupDetailUIEvent) {

    }

    fun initialize(groupId: Long){

    }
}