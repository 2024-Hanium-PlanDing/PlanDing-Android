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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.comst.domain.model.base.Schedule
import com.comst.presentation.ui.theme.PlanDingTheme
import com.comst.presentation.ui.util.bottomBorder
import com.comst.presentation.ui.util.endBorder
import kotlin.math.absoluteValue

@Composable
fun PDScheduleBarChart(scheduleList: List<Schedule>, days: List<String>) {
    val hours = (6..24).toList()
    val borderColor = Color.Gray
    val borderWidth = 0.5.dp

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(Color.White)
                .border(borderWidth, borderColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .border(borderWidth / 2, borderColor),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .border(borderWidth / 2, borderColor)
                        .bottomBorder(borderWidth / 2, borderColor)
                        .endBorder(borderWidth / 2, borderColor)
                )
                days.forEach { day ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(borderWidth / 2, borderColor)
                            .bottomBorder(borderWidth / 2, borderColor)
                            .endBorder(borderWidth / 2, borderColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = day, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
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
                        .weight(1f)
                        .fillMaxHeight()
                        .border(borderWidth / 2, borderColor)
                        .endBorder(borderWidth / 2, borderColor),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    hours.forEach { hour ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .border(borderWidth / 2, borderColor)
                                .bottomBorder(borderWidth / 2, borderColor)
                                .endBorder(borderWidth / 2, borderColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = String.format("%02d:00", hour), fontSize = 12.sp, color = Color.Black)
                        }
                    }
                }

                days.forEachIndexed { dayIndex, day ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(borderWidth / 2, borderColor)
                            .endBorder(borderWidth / 2, borderColor)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(0.dp)
                            ) {
                                hours.forEachIndexed { hourIndex, _ ->
                                    Box(
                                        modifier = Modifier
                                            .height(48.dp)
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .border(borderWidth / 2, borderColor)
                                            .bottomBorder(borderWidth / 2, borderColor)
                                            .endBorder(borderWidth / 2, borderColor),
                                    )
                                }
                            }

                            scheduleList.filter { it.day == day }.forEach { event ->
                                val topPadding = (event.startTime - 6).absoluteValue * 48.dp
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
        PDScheduleBarChart(
            scheduleList = listOf(),
            days = listOf()
        )
    }
}