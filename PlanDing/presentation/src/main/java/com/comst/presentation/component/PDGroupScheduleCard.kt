package com.comst.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.comst.domain.model.base.Schedule
import com.comst.domain.model.base.ScheduleType
import com.comst.presentation.ui.theme.BackgroundColor2
import com.comst.presentation.ui.theme.BackgroundColor4
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun PDGroupScheduleCard(
    schedule: Schedule,
    onShowScheduleDetail: (Schedule) -> Unit
) {
    var isContentVisible by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BackgroundColor2),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundColor2,
        )
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ){
                Text(
                    text = schedule.title,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Complete: ",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )

                Checkbox(
                    checked = schedule.complete,
                    onCheckedChange = null,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                )
            }

            Box(modifier = Modifier.fillMaxWidth()){
                Column {
                    Text(
                        text = "시간: ${schedule.startTime}:00 ~ ${schedule.endTime}:00",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .clickable { isContentVisible = !isContentVisible }
                    ) {
                        Text(
                            text = if (isContentVisible) "Hide Content" else "Show Content",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            imageVector = if (isContentVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (isContentVisible) "Hide Content" else "Show Content"
                        )
                    }

                    if (isContentVisible) {
                        Text(
                            text = schedule.content,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .background(BackgroundColor4, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        )
                    }
                }

                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        onShowScheduleDetail(schedule)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Go to Details"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PDGroupScheduleCardPreview(){
    PlanDingTheme{
        PDGroupScheduleCard(
            schedule = Schedule(
                scheduleId = 3231,
                title = "eruditi",
                content = "sit",
                startTime = 9662,
                endTime = 1860,
                day = "deterruisset",
                complete = false,
                groupName = null,
                type = ScheduleType.GROUP
            ),
            {}
        )
    }
}