package com.comst.presentation.main.schedule

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.presentation.component.PDCalendar
import com.comst.presentation.component.PDScheduleChart
import com.comst.presentation.component.PDScreenHeader
import com.comst.domain.model.base.ScheduleEvent
import com.comst.presentation.R
import com.comst.presentation.main.schedule.ScheduleContract.*
import com.comst.presentation.main.schedule.ScheduleContract.ScheduleUIEvent.*
import com.comst.presentation.main.schedule.addSchedule.AddScheduleDialog
import com.comst.presentation.ui.theme.BackgroundColor2
import com.comst.presentation.ui.theme.MainPurple200
import com.comst.presentation.ui.theme.PlanDingTheme

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ScheduleUISideEffect.ShowToast -> {
                    Toast.makeText(
                        context,
                        effect.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            PDScreenHeader(text = "스케줄")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White
                    ),
            ) {
                ScheduleTabs(
                    selectUIDate = uiState.selectUIDate,
                    selectDay = uiState.selectDay,
                    isTodayScheduleVisible = uiState.isTodayScheduleVisible,
                    todayPersonalScheduleEvents = uiState.todayPersonalScheduleEvents,
                    todayGroupScheduleEvents = uiState.todayGroupScheduleEvents,
                    onUIAction = viewModel::setEvent
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            PDScheduleChart(events = uiState.selectWeekScheduleEvents, days = uiState.selectedWeekdays)
        }
    }

    if (uiState.isBottomSheetVisible) {
        CalendarBottomSheet(
            viewModel = viewModel
        )
    }

    if (uiState.isAddScheduleDialogVisible) {
        AddScheduleDialog(
            date = uiState.selectLocalDate,
            onDismiss = {
                viewModel.setEvent(HideAddScheduleDialog)
            },
            onConfirm = { scheduleEvent ->
                viewModel.addSchedule(scheduleEvent = scheduleEvent)
                viewModel.setEvent(HideAddScheduleDialog)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarBottomSheet(
    viewModel: ScheduleViewModel
) {
    val calendarBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            viewModel.setEvent(CloseBottomSheetClick)
        },
        sheetState = calendarBottomSheetState,
    ) {
        PDCalendar(viewModel = viewModel)
    }
}

@Composable
private fun ScheduleTabs(
    selectUIDate: String,
    selectDay: String,
    isTodayScheduleVisible: Boolean,
    todayPersonalScheduleEvents: List<ScheduleEvent>,
    todayGroupScheduleEvents: List<ScheduleEvent>,
    onUIAction: (ScheduleUIEvent) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column {
        Row(
            modifier = Modifier
                .clickable {
                    onUIAction(OpenBottomSheetClick)
                }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "달력",
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                modifier = Modifier,
                text = "$selectUIDate $selectDay",
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = if (isTodayScheduleVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "오늘의 스케줄 토글",
                modifier = Modifier.clickable {
                    onUIAction(ToggleTodayScheduleVisibility)
                }
            )
        }

        if (isTodayScheduleVisible) {
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = BackgroundColor2,
                        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    )
            ) {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 }
                    ) {
                        Text(text = "개인일정", modifier = Modifier.padding(16.dp))
                    }
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 }
                    ) {
                        Text(text = "그룹일정", modifier = Modifier.padding(16.dp))
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    if (selectedTabIndex == 0) {
                        if (todayPersonalScheduleEvents.isEmpty()) {
                            NoScheduleContent()
                        } else {
                            ScheduleList(todayPersonalScheduleEvents)
                        }
                        FloatingActionButton(
                            modifier = Modifier
                                .size(80.dp)
                                .padding(16.dp)
                                .align(Alignment.BottomEnd),
                            containerColor = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(60.dp),
                            onClick = {
                                onUIAction(ShowAddScheduleDialog)
                            },
                        ) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add"
                            )
                        }
                    } else {
                        if (todayGroupScheduleEvents.isEmpty()) {
                            NoScheduleContent()
                        } else {
                            ScheduleList(todayGroupScheduleEvents)
                        }
                    }
                }
            }
        }
        
    }
}

@Composable
private fun NoScheduleContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(4f))
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(color = Color(0xFFF4F4F4), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = R.drawable.ic_main_schedule),
                contentDescription = "일정 없을 때 이미지"
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "등록된 일정이 없습니다.",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Box(modifier = Modifier.weight(6f))
    }
}

@Composable
private fun ScheduleList(
    events: List<ScheduleEvent>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = events.size,
            key = { index -> events[index].scheduleId }
        ) { index ->
            events[index].let {
                PersonalScheduleCard(schedule = it)
            }
            Divider()
        }
    }
}
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScheduleScreenPreview() {
    PlanDingTheme {
        ScheduleScreen(

        )
    }
}