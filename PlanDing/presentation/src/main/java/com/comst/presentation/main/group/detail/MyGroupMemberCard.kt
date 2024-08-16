package com.comst.presentation.main.group.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comst.domain.model.group.GroupUserInformationModel
import com.comst.presentation.component.PDProfileImage
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun MyGroupMemberCard(
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

        Text(
            text = "나",
            modifier = Modifier
                .size(20.dp)
                .background(color = Color.Black, shape = CircleShape)
                .wrapContentSize(align = Alignment.Center),
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = user.userName,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun GroupMemberCardPreview() {
    PlanDingTheme {
        MyGroupMemberCard(
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