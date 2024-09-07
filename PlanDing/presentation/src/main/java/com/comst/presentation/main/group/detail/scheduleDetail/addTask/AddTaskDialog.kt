package com.comst.presentation.main.group.detail.scheduleDetail.addTask

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.domain.util.DateUtils
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDButton
import com.comst.presentation.component.PDTextFieldOutLine
import com.comst.presentation.component.PDTimeDropdownMenu
import com.comst.presentation.main.group.detail.addSchedule.AddGroupScheduleContract
import com.comst.presentation.main.group.detail.scheduleDetail.addTask.AddTaskContract.AddTaskIntent
import com.comst.presentation.main.group.detail.scheduleDetail.addTask.AddTaskContract.AddTaskSideEffect
import com.comst.presentation.main.group.detail.scheduleDetail.addTask.AddTaskContract.AddTaskUIState
import com.comst.presentation.model.group.TaskUserUIModel
import com.comst.presentation.model.group.socket.SendCreateTaskDTO
import com.comst.presentation.ui.theme.Background0
import com.comst.presentation.ui.theme.Background50
import com.comst.presentation.ui.theme.Background60
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun AddTaskDialog(
    viewModel: AddTaskViewModel = hiltViewModel(),
    groupCode: String,
    scheduleId: Long,
    onDismiss: () -> Unit,
    onConfirm: (SendCreateTaskDTO) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(
            groupCode = groupCode,
            scheduleId = scheduleId
        )
    }

    val handleEffect: (AddTaskSideEffect) -> Unit = { effect ->
        when(effect) {
            is AddTaskSideEffect.SuccessCreateTask -> {
                onConfirm(effect.task)
                viewModel.initialize(
                    groupCode = groupCode,
                    scheduleId = scheduleId
                )
            }
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        AddTaskDialog(
            uiState = uiState,
            setIntent = viewModel::setIntent,
            onDismiss = onDismiss
        )

    }
}
@Composable
fun AddTaskDialog(
    uiState: AddTaskUIState,
    setIntent: (AddTaskIntent) -> Unit = {},
    onDismiss: () -> Unit = {}
){
    AlertDialog(
        onDismissRequest = {
            setIntent(AddTaskIntent.Initialize(
                groupCode = uiState.groupCode,
                scheduleId = uiState.scheduleId
            ))
            onDismiss()
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "할 일 추가")
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DateSelectTab(
                        modifier = Modifier.weight(1f),
                        serverDate = uiState.schedule.scheduleDate
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    
                    PDTimeDropdownMenu(
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp),
                        selectedTime = uiState.dueTime,
                        onConfirm = {
                            setIntent(AddTaskIntent.SelectedDueTime(it))
                        }
                    )
                }

                PDTextFieldOutLine(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    label = "제목을 입력해주세요",
                    value = uiState.taskTitle,
                    onValueChange = { taskTitle ->
                        setIntent(AddTaskIntent.TaskTitleChange(taskTitle))
                    }
                )
                PDTextFieldOutLine(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp, max = 160.dp),
                    label = "일정 내용을 입력해주세요",
                    value = uiState.taskContent,
                    onValueChange = { taskContent ->
                        setIntent(AddTaskIntent.TaskContentChange(taskContent))
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ParticipantSelection(
                    groupMember = uiState.groupMember,
                    isGroupMemberListVisible = uiState.isGroupMemberListVisible,
                    setIntent
                )
            }
        },
        confirmButton = {
            PDButton(
                onClick = {
                    setIntent(AddTaskIntent.CreateTask)
                },
                text = "생성",
                modifier = Modifier.fillMaxWidth()
            )
        },
        dismissButton = {
            Button(
                onClick = {
                    setIntent(AddTaskIntent.Initialize(
                        groupCode = uiState.groupCode,
                        scheduleId = uiState.scheduleId
                    ))
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Background60,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "닫기",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Composable
private fun DateSelectTab(
    modifier: Modifier,
    serverDate: String
) {

    val uiDate = DateUtils.serverDateToUIDate(serverDate)

    Row(
        modifier = modifier
            .height(50.dp)
            .background(
                shape = RoundedCornerShape(8.dp),
                color = Background50
            )
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Filled.DateRange,
            tint = Color.Black,
            contentDescription = "달력",
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = uiDate,
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun ParticipantSelection(
    groupMember: List<TaskUserUIModel>,
    isGroupMemberListVisible: Boolean,
    setIntent: (AddTaskIntent) -> Unit = {}
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(8.dp),
                color = Background0
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    setIntent(AddTaskIntent.ToggleGroupMemberListVisibility)
                }
        ) {
            Text(
                text = "인원 설정",
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = if (isGroupMemberListVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "인원 설정 토글",
            )
        }

        if (isGroupMemberListVisible){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                items(
                    count = groupMember.size,
                    key = { index ->  groupMember[index].userCode }
                ){ index ->
                    ParticipantCard(
                        groupMember = groupMember[index],
                        checkBoxClick = {  member ->
                            setIntent(AddTaskIntent.MemberCheckBoxClick(member))
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddTaskDialogPreview(){
    PlanDingTheme {
        AddTaskDialog(
            uiState = AddTaskUIState()
        )
    }
}