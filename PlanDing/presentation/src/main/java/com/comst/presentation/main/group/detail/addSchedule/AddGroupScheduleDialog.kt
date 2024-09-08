package com.comst.presentation.main.group.detail.addSchedule

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDButton
import com.comst.presentation.component.PDTextFieldOutLine
import com.comst.presentation.component.PDTimeDropdownMenu
import com.comst.presentation.main.group.detail.addSchedule.AddGroupScheduleContract.AddGroupScheduleIntent
import com.comst.presentation.main.group.detail.addSchedule.AddGroupScheduleContract.AddGroupScheduleSideEffect
import com.comst.presentation.main.group.detail.addSchedule.AddGroupScheduleContract.AddGroupScheduleUIState
import com.comst.presentation.model.group.GroupProfileUIModel
import com.comst.presentation.model.group.socket.SendCreateScheduleDTO
import com.comst.presentation.ui.theme.Background0
import com.comst.presentation.ui.theme.Background60
import com.comst.presentation.ui.theme.PlanDingTheme
import java.time.LocalDate

@Composable
fun AddGroupScheduleDialog(
    viewModel: AddGroupScheduleViewModel = hiltViewModel(),
    groupProfile: GroupProfileUIModel,
    date: LocalDate,
    onDismiss: () -> Unit,
    onConfirm: (SendCreateScheduleDTO) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(
            groupProfile = groupProfile,
            date = date
        )
    }

    val context = LocalContext.current
    val handleEffect: (AddGroupScheduleSideEffect) -> Unit = { effect ->
        when (effect) {
            is AddGroupScheduleSideEffect.SuccessCreateGroupSchedule -> {
                onConfirm(effect.schedule)
                viewModel.initialize(
                    groupProfile = groupProfile,
                    date = date
                )
            }
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        AddGroupScheduleDialog(
            uiState = uiState,
            setIntent = viewModel::setIntent,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun AddGroupScheduleDialog(
    uiState: AddGroupScheduleUIState,
    setIntent: (AddGroupScheduleIntent) -> Unit = {},
    onDismiss: () -> Unit = {}
){
    AlertDialog(
        onDismissRequest = {
            setIntent(AddGroupScheduleIntent.Initialize(
                groupProfile = uiState.groupProfile,
                date = uiState.date
            ))
            onDismiss()
        },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(uiState.groupProfile.thumbnailUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .height(32.dp)
                        .width(40.dp),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "일정 추가")
            }
        },
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
                        setIntent(AddGroupScheduleIntent.TitleChange(it))
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
                        setIntent(AddGroupScheduleIntent.DescriptionChange(it))
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PDTimeDropdownMenu(
                        modifier = Modifier.width(100.dp),
                        selectedTime = uiState.startTime,
                        onConfirm = {
                            setIntent(AddGroupScheduleIntent.SelectedStartTime(it))
                        }
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(text = "부터")
                    Spacer(modifier = Modifier.weight(1f))
                    PDTimeDropdownMenu(
                        modifier = Modifier.width(100.dp),
                        selectedTime = uiState.endTime,
                        onConfirm = {
                            setIntent(AddGroupScheduleIntent.SelectedEndTime(it))
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
                    setIntent(AddGroupScheduleIntent.CreateGroupSchedule)
                },
                text = "생성",
                modifier = Modifier.fillMaxWidth()
            )
        },
        dismissButton = {
            Button(
                onClick = {
                    setIntent(AddGroupScheduleIntent.Initialize(
                        groupProfile = uiState.groupProfile,
                        date = uiState.date
                    ))
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
        AddGroupScheduleDialog(
            uiState = AddGroupScheduleUIState()
        )
    }
}