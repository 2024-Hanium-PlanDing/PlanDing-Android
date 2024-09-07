package com.comst.presentation.main.group.detail.scheduleDetail.addTask

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comst.presentation.component.PDProfileImage
import com.comst.presentation.model.group.TaskUserUIModel
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun ParticipantCard(
    groupMember: TaskUserUIModel,
    checkBoxClick: (TaskUserUIModel) -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PDProfileImage(
            modifier = Modifier.size(36.dp),
            profileImageUrl = groupMember.profileImage,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = groupMember.username)

        Spacer(modifier = Modifier.weight(1f))

        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                isChecked = checked
                checkBoxClick(groupMember)
            },
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview
@Composable fun ParticipantCardPreview(){
    PlanDingTheme {
        ParticipantCard(
            groupMember = TaskUserUIModel(
                userCode = "constituam",
                username = "Candy Fischer",
                profileImage = "velit"
            ),
            {}
        )
    }
}