package com.comst.presentation.main.schedule

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import com.comst.domain.model.base.Schedule
import com.comst.presentation.R
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.main.schedule.ScheduleContract.*
import com.comst.presentation.main.schedule.addSchedule.AddScheduleDialog
import com.comst.presentation.ui.theme.BackgroundColor2
import com.comst.presentation.ui.theme.PlanDingTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val handleEffect: (ScheduleSideEffect) -> Unit = { effect ->
        when (effect) {
            else -> {}
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
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
                    Column(
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .padding(horizontal = 16.dp)
                    ) {

                        ScheduleHeader(
                            selectUIDate = uiState.selectUIDate,
                            selectDay = uiState.selectDay,
                            isTodayScheduleVisible = uiState.isTodayScheduleVisible,
                            onUIAction = viewModel::setIntent
                        )

                        if (uiState.isTodayScheduleVisible) {
                            Spacer(modifier = Modifier.height(8.dp))
                            ScheduleTabs(
                                todayPersonalSchedules = uiState.todayPersonalScheduleEvents,
                                todayGroupSchedules = uiState.todayGroupScheduleEvents,
                                onUIAction = viewModel::setIntent
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                PDScheduleChart(
                    events = uiState.selectWeekScheduleEvents,
                    days = uiState.selectedWeekdays
                )
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
                    viewModel.setIntent(ScheduleIntent.HideAddScheduleDialog)
                },
                onConfirm = { scheduleEvent ->
                    viewModel.addSchedule(scheduleEvent)
                    viewModel.setIntent(ScheduleIntent.HideAddScheduleDialog)
                }
            )
        }
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
            viewModel.setIntent(ScheduleIntent.CloseBottomSheetClick)
        },
        sheetState = calendarBottomSheetState,
    ) {
        PDCalendar(viewModel = viewModel)
    }
}

@Composable
private fun ScheduleHeader(
    selectUIDate: String,
    selectDay: String,
    isTodayScheduleVisible: Boolean,
    onUIAction: (ScheduleIntent) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onUIAction(ScheduleIntent.OpenBottomSheetClick) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = "달력",
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = "$selectUIDate $selectDay",
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = if (isTodayScheduleVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "오늘의 스케줄 토글",
            modifier = Modifier.clickable { onUIAction(ScheduleIntent.ToggleTodayScheduleVisibility) }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ScheduleTabs(
    todayPersonalSchedules: List<Schedule>,
    todayGroupSchedules: List<Schedule>,
    onUIAction: (ScheduleIntent) -> Unit
) {
    val pages = listOf("개인일정", "그룹일정")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var periodIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            periodIndex = page
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BackgroundColor2,
                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
            )
    ) {
        BoxWithConstraints {
            val tabWidth = maxWidth / pages.size

            TabRow(
                selectedTabIndex = periodIndex,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[periodIndex])
                            .width(tabWidth)
                            .height(4.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                },
            ) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        selected = periodIndex == index,
                        onClick = {
                            coroutineScope.launch {
                                periodIndex = index
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        Text(
                            text = title,
                            maxLines = 1,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            ScheduleTabsContent(
                page = page,
                todayPersonalSchedules = todayPersonalSchedules,
                todayGroupSchedules = todayGroupSchedules,
                onUIAction = onUIAction
            )
        }
    }
}

@Composable
private fun ScheduleTabsContent(
    page: Int,
    todayPersonalSchedules: List<Schedule>,
    todayGroupSchedules: List<Schedule>,
    onUIAction: (ScheduleIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        when (page) {
            0 -> {
                if (todayPersonalSchedules.isEmpty()) {
                    NoScheduleContent()
                } else {
                    ScheduleList(todayPersonalSchedules)
                }
                FloatingActionButton(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(16.dp)
                        .align(Alignment.BottomEnd),
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(60.dp),
                    onClick = {
                        onUIAction(ScheduleIntent.ShowAddScheduleDialog)
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                }
            }
            1 -> {
                if (todayGroupSchedules.isEmpty()) {
                    NoScheduleContent()
                } else {
                    ScheduleList(todayGroupSchedules)
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
    events: List<Schedule>,
) {
    val sortedEvents = remember(events) { events.sortedBy { it.startTime } }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = sortedEvents.size,
            key = { index -> sortedEvents[index].scheduleId }
        ) { index ->
            sortedEvents[index].let {
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
        ScheduleScreen()
    }
}