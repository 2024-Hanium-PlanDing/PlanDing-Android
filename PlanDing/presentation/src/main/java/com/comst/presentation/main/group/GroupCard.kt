package com.comst.presentation.main.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comst.presentation.component.PDProfileImage
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun GroupCard(
    groupName: String,
    groupDescription: String,
    groupImageUrl: String?
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.Top
    ) {
        PDProfileImage(
            modifier = Modifier.size(120.dp),
            profileImageUrl = groupImageUrl
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = groupName,
                style =  MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(4.dp))

            var maxLines by remember(groupDescription) { mutableStateOf(1) }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = groupDescription,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                style =  MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
private fun GroupCardPreview(){
    PlanDingTheme {
        GroupCard(
            groupName = "Wyatt Alston",
            groupDescription = "amet",
            groupImageUrl = null
        )
    }
}