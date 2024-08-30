package com.comst.presentation.main.schedule.addSchedule

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.domain.model.base.Schedule
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDButton
import com.comst.presentation.component.PDTextFieldOutLine
import com.comst.presentation.component.PDTimeDropdownMenu
import com.comst.presentation.main.schedule.addSchedule.AddPersonalScheduleContract.AddPersonalScheduleIntent
import com.comst.presentation.main.schedule.addSchedule.AddPersonalScheduleContract.AddPersonalScheduleSideEffect
import com.comst.presentation.main.schedule.addSchedule.AddPersonalScheduleContract.AddPersonalScheduleUIState
import com.comst.presentation.ui.theme.Background0
import com.comst.presentation.ui.theme.PlanDingTheme
import java.time.LocalDate

@Composable
fun AddPersonalScheduleDialog(
    viewModel: AddPersonalScheduleViewModel = hiltViewModel(),
    date: LocalDate,
    onDismiss: () -> Unit,
    onConfirm: (Schedule) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(date)
    }

    val context = LocalContext.current
    val handleEffect: (AddPersonalScheduleSideEffect) -> Unit = { effect ->
        when (effect) {
            is AddPersonalScheduleSideEffect.SuccessCreatePersonalSchedule -> {
                onConfirm(effect.schedule)
                viewModel.initialize(date)
            }
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        AddPersonalScheduleDialog(
            uiState = uiState,
            setIntent = viewModel::setIntent,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun AddPersonalScheduleDialog(
    uiState: AddPersonalScheduleUIState,
    setIntent: (AddPersonalScheduleIntent) -> Unit = {},
    onDismiss: () -> Unit = {}
){
    AlertDialog(
        onDismissRequest = {
            setIntent(AddPersonalScheduleIntent.Initialize(uiState.date))
            onDismiss()
        },
        title = { Text(text = "일정 추가") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = uiState.uiDate,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                PDTextFieldOutLine(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    label = "제목을 입력해주세요",
                    value = uiState.title,
                    onValueChange = {
                        setIntent(AddPersonalScheduleIntent.TitleChange(it))
                    }
                )
                PDTextFieldOutLine(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                        .padding(vertical = 8.dp),
                    label = "일정 내용을 입력해주세요",
                    value = uiState.content,
                    onValueChange = {
                        setIntent(AddPersonalScheduleIntent.DescriptionChange(it))
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PDTimeDropdownMenu(
                        selectedTime = uiState.startTime,
                        onConfirm = {
                            setIntent(AddPersonalScheduleIntent.SelectedStartTime(it))
                        }
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(text = "부터")
                    Spacer(modifier = Modifier.weight(1f))
                    PDTimeDropdownMenu(
                        selectedTime = uiState.endTime,
                        onConfirm = {
                            setIntent(AddPersonalScheduleIntent.SelectedEndTime(it))
                        }
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(text = "까지")
                }
            }
        },
        confirmButton = {
            PDButton(
                onClick = {
                    setIntent(AddPersonalScheduleIntent.CreatePersonalSchedule)
                },
                text = "생성",
                modifier = Modifier.fillMaxWidth()
            )
        },
        dismissButton = {
            Button(
                onClick = {
                    setIntent(AddPersonalScheduleIntent.Initialize(uiState.date))
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Background0,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "닫기",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        shape = RoundedCornerShape(8.dp)
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddScheduleDialogPreview() {
    PlanDingTheme {
        AddPersonalScheduleDialog(
            AddPersonalScheduleUIState()
        )
    }
}