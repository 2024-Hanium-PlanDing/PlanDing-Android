package com.comst.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.comst.domain.model.base.ScheduleEvent
import com.comst.presentation.ui.theme.BackgroundColor3
import com.comst.presentation.ui.theme.MainPurple200
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun PDScheduleChart(events: List<ScheduleEvent>, days: List<String>) {
    val hours = (6..24).toList()
    val borderColor = Color.Gray
    val textColor = Color.Black

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(BackgroundColor3)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .border(0.5.dp, borderColor),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(modifier = Modifier.width(48.dp))
                days.forEach { day ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(0.5.dp, borderColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = day, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Start
            ) {
                Column(
                    modifier = Modifier
                        .width(48.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    hours.forEach { hour ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .border(0.5.dp, borderColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "$hour:00", fontSize = 12.sp, color = textColor)
                        }
                    }
                }

                days.forEach { day ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(0.5.dp, borderColor),
                                verticalArrangement = Arrangement.spacedBy(0.dp)
                            ) {
                                hours.forEach { _ ->
                                    Box(
                                        modifier = Modifier
                                            .height(48.dp)
                                            .fillMaxWidth()
                                            .background(BackgroundColor3)
                                            .border(0.5.dp, borderColor)
                                    )
                                }
                            }

                            events.filter { it.day == day }.forEach { event ->
                                val topPadding = (event.startTime - 6) * 48.dp
                                val height = (event.endTime - event.startTime) * 48.dp
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = topPadding)
                                        .height(height)
                                        .align(Alignment.TopCenter)
                                        .background(Color(0xFFB3C1FC), shape = RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(text = event.title, color = Color.White, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PDScheduleChartPreview(){
    PlanDingTheme {
        PDScheduleChart(
            events = listOf(),
            days = listOf()
        )
    }
}