package com.comst.presentation.main.group.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comst.domain.model.chat.ChatMessageModel
import com.comst.domain.util.DateUtils
import com.comst.presentation.component.PDProfileImage
import com.comst.presentation.ui.theme.BackgroundColor1
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun OtherChatCard(
    chat: ChatMessageModel
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val maxWidth = maxWidth

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            PDProfileImage(
                modifier = Modifier.size(48.dp),
                profileImageUrl = chat.profileImage,
            )

            Column(
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = chat.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = chat.message,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .widthIn(max = maxWidth * 0.6f)
                            .background(
                                color = BackgroundColor1,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    )

                    Text(
                        text = DateUtils.getTimeFromDateTimeString(chat.createdAt),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

            }

        }
    }
}

@Preview
@Composable
private fun OtherChatCardPreview() {
    PlanDingTheme {

    }
}