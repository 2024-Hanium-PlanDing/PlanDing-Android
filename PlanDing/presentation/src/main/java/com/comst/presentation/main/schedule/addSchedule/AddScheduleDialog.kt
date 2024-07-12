package com.comst.presentation.main.schedule.addSchedule

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.domain.model.base.Schedule
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDButton
import com.comst.presentation.component.PDTextFiledOutLine
import com.comst.presentation.component.PDTimeDropdownMenu
import com.comst.presentation.main.schedule.addSchedule.AddScheduleContract.AddScheduleIntent
import com.comst.presentation.main.schedule.addSchedule.AddScheduleContract.AddScheduleSideEffect
import com.comst.presentation.ui.theme.BackgroundColor3
import com.comst.presentation.ui.theme.PlanDingTheme
import java.time.LocalDate

@Composable
fun AddScheduleDialog(
    viewModel: AddScheduleViewModel = hiltViewModel(),
    date: LocalDate,
    onDismiss: () -> Unit,
    onConfirm: (Schedule) -> Unit
) {
    LaunchedEffect(date) {
        viewModel.initialize(date)
    }

    val context = LocalContext.current
    val handleEffect: (AddScheduleSideEffect) -> Unit = { effect ->
        when(effect) {
            is AddScheduleSideEffect.SuccessCreateSchedule -> onConfirm(effect.schedule)
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        AlertDialog(
            onDismissRequest = onDismiss,
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
                    PDTextFiledOutLine(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = "제목을 입력해주세요",
                        value = uiState.title,
                        onValueChange = {
                            viewModel.setIntent(AddScheduleIntent.TitleChange(it))
                        }
                    )
                    PDTextFiledOutLine(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                            .padding(vertical = 8.dp),
                        label = "일정 내용을 입력해주세요",
                        value = uiState.content,
                        onValueChange = {
                            viewModel.setIntent(AddScheduleIntent.DescriptionChange(it))
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
                                viewModel.setIntent(AddScheduleIntent.SelectedStartTime(it))
                            }
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                        Text(text = "부터")
                        Spacer(modifier = Modifier.weight(1f))
                        PDTimeDropdownMenu(
                            selectedTime = uiState.endTime,
                            onConfirm = {
                                viewModel.setIntent(AddScheduleIntent.SelectedEndTime(it))
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
                        viewModel.setIntent(AddScheduleIntent.CreateSchedule)
                    },
                    text = "생성",
                    modifier = Modifier.fillMaxWidth()
                )
            },
            dismissButton = {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BackgroundColor3,
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
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddScheduleDialogPreview() {
    PlanDingTheme {
        AddScheduleDialog(
            date = LocalDate.now(),
            onDismiss = {},
            onConfirm = {}
        )
    }
}