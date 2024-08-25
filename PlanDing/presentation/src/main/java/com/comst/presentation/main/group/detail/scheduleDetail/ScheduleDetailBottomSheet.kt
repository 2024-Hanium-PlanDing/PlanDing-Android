package com.comst.presentation.main.group.detail.scheduleDetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.domain.model.base.Schedule
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.*
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun ScheduleDetailBottomSheet(
    viewModel: ScheduleDetailViewModel = hiltViewModel(),
    groupCode: String,
    schedule: Schedule,
    onCloseBottomSheet:() -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(
            groupCode = groupCode,
            schedule = schedule
        )
    }

    val handleEffect: (ScheduleDetailSideEffect) -> Unit = { effect ->

    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        ScheduleDetailBottomSheet(
            uiState = uiState,
            onCloseBottomSheet = onCloseBottomSheet
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleDetailBottomSheet(
    uiState: ScheduleDetailUIState,
    setIntent: (ScheduleDetailIntent) -> Unit = {},
    onCloseBottomSheet: () -> Unit = {},
){
    val scheduleDetailBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            onCloseBottomSheet()
        },
        sheetState = scheduleDetailBottomSheetState
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = uiState.schedule.title)
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScheduleDetailBottomSheetPreview(){
    PlanDingTheme {
        ScheduleDetailBottomSheet(
            uiState = ScheduleDetailUIState()
        )
    }
}