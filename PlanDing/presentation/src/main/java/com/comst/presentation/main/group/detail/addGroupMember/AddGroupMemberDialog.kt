package com.comst.presentation.main.group.detail.addGroupMember

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDButton
import com.comst.presentation.component.PDTextFieldOutLine
import com.comst.presentation.component.PDTimeDropdownMenu
import com.comst.presentation.main.group.detail.addGroupMember.AddGroupMemberContract.AddGroupMemberIntent
import com.comst.presentation.main.group.detail.addGroupMember.AddGroupMemberContract.AddGroupMemberSideEffect
import com.comst.presentation.main.group.detail.addSchedule.AddGroupScheduleContract
import com.comst.presentation.ui.theme.BackgroundColor3
import com.comst.presentation.ui.theme.MainPurple600
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun AddGroupMemberDialog(
    viewModel: AddGroupMemberViewModel = hiltViewModel(),
    groupCode: String,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(
            groupCode = groupCode
        )
    }

    val context = LocalContext.current
    val handleEffect: (AddGroupMemberSideEffect) -> Unit = { effect ->
        when (effect) {
            is AddGroupMemberSideEffect.SuccessInviteGroupMember -> {
                onDismiss()
            }
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                viewModel.initialize(
                    groupCode = groupCode
                )
                onDismiss()
            },
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "그룹원 추가")
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "코드를 전달 받았다면 코드를 입력하세요",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                    )
                    PDTextFieldOutLine(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = "유저 코드를 입력해주세요",
                        value = uiState.userCode,
                        onValueChange = {
                            viewModel.setIntent(
                                AddGroupMemberIntent.UserCodeChange(
                                    it
                                )
                            )
                        }
                    )

                }
            },
            confirmButton = {
                PDButton(
                    onClick = {
                        viewModel.setIntent(AddGroupMemberIntent.InviteGroupMember)
                    },
                    text = "초대",
                    modifier = Modifier.fillMaxWidth()
                )
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.initialize(
                            groupCode = groupCode
                        )
                        onDismiss()
                    },
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
private fun AddGroupMemberDialogPreview() {
    PlanDingTheme {
        AddGroupMemberDialog(
            groupCode = "fabellas",
            onDismiss = {}
        )
    }
}