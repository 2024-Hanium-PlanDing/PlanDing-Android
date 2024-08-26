package com.comst.presentation.main.group.detail.scheduleDetail

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.domain.model.base.Schedule
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDDropdownSelector
import com.comst.presentation.main.group.detail.scheduleDetail.ScheduleDetailContract.*
import com.comst.presentation.ui.theme.BackgroundColor1
import com.comst.presentation.ui.theme.BackgroundColor4
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
            setIntent = viewModel::setIntent,
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
        ScheduleDetailContent(
            uiState = uiState,
            setIntent = setIntent
        )
    }
}

@Composable
private fun ScheduleDetailContent(
    uiState: ScheduleDetailUIState,
    setIntent: (ScheduleDetailIntent) -> Unit = {}
){
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = uiState.schedule.title,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "일정 내용",
            style = MaterialTheme.typography.labelSmall
        )

        ClickableText(
            text = buildAnnotatedString { append(uiState.schedule.content) },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 8.dp)
                .background(BackgroundColor1, shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            maxLines = if (isExpanded) Int.MAX_VALUE else 4,
            overflow = TextOverflow.Ellipsis,
            onClick = {
                isExpanded = !isExpanded
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "해야 할 일",
            style = MaterialTheme.typography.labelSmall
        )

        TaskArea(
            selectedOption = uiState.selectedOption,
            setIntent = setIntent
        )
    }
}

@Composable
private fun TaskArea(
    selectedOption: TaskStatus,
    setIntent: (ScheduleDetailIntent) -> Unit = {}
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 8.dp)
            .background(BackgroundColor1, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TaskStatus.values().forEach { status ->
                    Text(
                        text = status.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedOption == status) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clickable {
                                setIntent(ScheduleDetailIntent.SelectTaskStatusOption(status))
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScheduleDetailBottomSheetPreview(){
    PlanDingTheme {
        ScheduleDetailContent(
            uiState = ScheduleDetailUIState()
        )
    }
}