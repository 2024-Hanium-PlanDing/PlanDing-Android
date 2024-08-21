package com.comst.presentation.main.group.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comst.domain.model.group.GroupUserInformationModel
import com.comst.presentation.component.PDProfileImage
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun OtherGroupMemberCard(
    user: GroupUserInformationModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PDProfileImage(
            modifier = Modifier.size(48.dp),
            profileImageUrl = user.profileImageUrl,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = user.userName)
    }
}

@Preview
@Composable
private fun GroupMemberCardPreview() {
    PlanDingTheme {
        OtherGroupMemberCard(
            user = GroupUserInformationModel(
                id = 4880,
                userCode = "possit",
                userName = "Alisha Burton",
                email = "ferdinand.serrano@example.com",
                profileImageUrl = "http://www.bing.com/search?q=equidem",
                hasPermission = false
            )

        )
    }
}