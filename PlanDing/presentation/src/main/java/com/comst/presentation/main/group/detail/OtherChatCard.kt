package com.comst.presentation.main.group.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.comst.presentation.component.PDProfileImage
import com.comst.presentation.ui.theme.BackgroundColor3
import com.comst.presentation.ui.theme.MainPurple600
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun OtherChatCard(
    message: String,
    time: String
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
                profileImageUrl = null,
            )

            Column(
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "nickname",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = message,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .widthIn(max = maxWidth * 0.6f)
                            .background(
                                color = BackgroundColor3,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    )

                    Text(
                        text = time,
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
        OtherChatCard(
            message = "auctor",
            time = "cum"
        )
    }
}