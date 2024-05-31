package com.comst.presentation.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.comst.domain.util.DateUtils
import com.comst.presentation.R
import com.comst.presentation.main.personal_schedule.PersonalScheduleUIAction
import com.comst.presentation.main.personal_schedule.PersonalScheduleViewModel
import java.util.Calendar

@Composable
fun PDCalendar(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: PersonalScheduleViewModel,
) {
    val state = viewModel.container.stateFlow.collectAsState().value
    val initialDate = remember { DateUtils.uiDateToDate(state.selectUIDate) }
    val calendar = remember { Calendar.getInstance().apply { time = initialDate } }
    var displayedMonth by remember { mutableIntStateOf(calendar.get(Calendar.MONTH)) }
    var displayedYear by remember { mutableIntStateOf(calendar.get(Calendar.YEAR)) }
    var selectedDate by remember { mutableIntStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    Column {
        CalendarHeader(
            displayedMonth,
            displayedYear,
            onMonthChange = { newMonth, newYear ->
                displayedMonth = newMonth
                displayedYear = newYear
                calendar.set(Calendar.MONTH, newMonth)
                calendar.set(Calendar.YEAR, newYear)
            }
        )
        CalendarBody(
            displayedMonth,
            displayedYear,
            selectedDate,
            onDateSelected = { day ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(Calendar.YEAR, displayedYear)
                    set(Calendar.MONTH, displayedMonth)
                    set(Calendar.DAY_OF_MONTH, day)
                }
                viewModel.onUIAction(PersonalScheduleUIAction.SelectedDate(selectedCalendar.time))
            }
        )
    }
}


@Composable
fun CalendarHeader(month: Int, year: Int, onMonthChange: (Int, Int) -> Unit) {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                val newYear = year - 1
                onMonthChange(month, newYear)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_doubble_left_24),
                    contentDescription = "Previous Year"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                val newMonth = if (month == 0) 11 else month - 1
                val newYear = if (month == 0) year - 1 else year
                onMonthChange(newMonth, newYear)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left_24),
                    contentDescription = "Previous Month"
                )
            }
        }

        Text(
            text = "${months[month]} $year",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                val newMonth = if (month == 11) 0 else month + 1
                val newYear = if (month == 11) year + 1 else year
                onMonthChange(newMonth, newYear)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right_24),
                    contentDescription = "Next Month"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                val newYear = year + 1
                onMonthChange(month, newYear)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_double_right_24),
                    contentDescription = "Next Year"
                )
            }
        }
    }
}

@Composable
fun CalendarBody(
    month: Int, year: Int,
    selectedDate: Int,
    onDateSelected: (Int) -> Unit
) {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.MONTH, month)
        set(Calendar.YEAR, year)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1 // Calendar.SUNDAY is 1, need to start from 0

    Column {
        Row {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        var day = 1
        for (week in 0 until 6) {
            Row {
                for (d in 0 until 7) {
                    if (week == 0 && d < firstDayOfWeek || day > daysInMonth) {
                        Text(
                            text = "",
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        val currentDay = day
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(
                                    if (currentDay == selectedDate) MaterialTheme.colorScheme.primaryContainer else Color(
                                        0xFFF9FAFF
                                    )
                                )
                                .clickable { onDateSelected(currentDay) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$currentDay",
                                color = if (currentDay == selectedDate) Color.Black else MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            )
                        }
                        day++
                    }
                }
            }
        }
    }
}