package com.comst.presentation.main.group.detail.scheduleDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comst.presentation.R
import com.comst.presentation.component.PDProfileImage
import com.comst.presentation.model.group.TaskUIModel
import com.comst.presentation.model.group.TaskUserUIModel
import com.comst.presentation.ui.theme.Background100
import com.comst.presentation.ui.theme.Background300
import com.comst.presentation.ui.theme.Background500
import com.comst.presentation.ui.theme.Background60
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun TaskCard(
    userCode: String,
    task: TaskUIModel,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                if (userCode == task.manager.userCode) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                        contentDescription = "More options",
                        tint = Background100,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Text(
                text = "Deadline: ${task.deadline}",
                style = MaterialTheme.typography.labelSmall,
                color = Background300,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = task.content,
                style = MaterialTheme.typography.bodySmall,
                color = Background500,
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ){
                if (task.users.size > 1) {
                    Text(
                        text = "${task.users.size}",
                        color = Background500,
                        modifier = Modifier
                            .background(color = Background60, shape = RoundedCornerShape(50))
                            .padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                }
                PDProfileImage(
                    modifier = Modifier.size(30.dp),
                    profileImageUrl = task.manager.profileImage
                )
            }
        }
    }
}

@Preview
@Composable
private fun TaskCardPreview() {
    PlanDingTheme {
        TaskCard(
            userCode = "aa",
            task = TaskUIModel(
                id = 4829,
                plannerNumber = 6200,
                title = "a",
                content = "alterum",
                status = "diam",
                deadline = "duo",
                manager = TaskUserUIModel(
                    userCode = "massa",
                    username = "Hunter Fuller",
                    profileImage = "finibus"
                ),
                users = listOf()
            )

        )
    }
}