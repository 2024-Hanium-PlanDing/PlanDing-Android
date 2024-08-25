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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.domain.model.base.Schedule
import com.comst.domain.util.DateUtils
import com.comst.presentation.R
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDCalendarBottomSheet
import com.comst.presentation.component.PDScheduleBarChart
import com.comst.presentation.component.PDScreenHeader
import com.comst.presentation.main.schedule.ScheduleContract.*
import com.comst.presentation.main.schedule.addSchedule.AddPersonalScheduleDialog
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
    val handleEffect: (ScheduleSideEffect) -> Unit = { effect ->
        when (effect) {
            else -> {}
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        ScheduleScreen(
            uiState = uiState,
            setIntent = viewModel::setIntent,
            setEvent = viewModel::setEvent
        )
    }
}

@Composable
private fun ScheduleScreen(
    uiState: ScheduleUIState,
    setIntent: (ScheduleIntent) -> Unit = {},
    setEvent : (ScheduleEvent) -> Unit = {}
){
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
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .background(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {

                    DateSelectTab(
                        selectUIDate = uiState.selectUIDate,
                        selectDay = uiState.selectDay,
                        isTodayScheduleVisible = uiState.isTodayScheduleVisible,
                        setIntent = setIntent
                    )

                    if (uiState.isTodayScheduleVisible) {
                        ScheduleTabs(
                            todayPersonalSchedules = uiState.todayPersonalScheduleList,
                            todayGroupSchedules = uiState.todayGroupScheduleList,
                            setIntent = setIntent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            PDScheduleBarChart(
                scheduleList = uiState.selectWeekScheduleList,
                days = uiState.selectedWeekdays
            )
        }
    }

    if (uiState.isCalendarBottomSheetVisible) {
        PDCalendarBottomSheet(
            date = DateUtils.uiDateToDate(uiState.selectUIDate),
            onCloseBottomSheet = {
                setIntent(ScheduleIntent.CloseCalendarBottomSheet)
            },
            onDateSelected = { date ->
                setIntent(ScheduleIntent.SelectDate(date))
            }
        )
    }

    if (uiState.isAddScheduleDialogVisible) {
        AddPersonalScheduleDialog(
            date = uiState.selectLocalDate,
            onDismiss = {
                setIntent(ScheduleIntent.HideAddScheduleDialog)
            },
            onConfirm = { schedule ->
                setEvent(ScheduleEvent.AddTodaySchedule(schedule))
                setIntent(ScheduleIntent.HideAddScheduleDialog)
            }
        )
    }
}

@Composable
private fun DateSelectTab(
    selectUIDate: String,
    selectDay: String,
    isTodayScheduleVisible: Boolean,
    setIntent: (ScheduleIntent) -> Unit
) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .clickable { setIntent(ScheduleIntent.OpenCalendarBottomSheet) },
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
            modifier = Modifier.clickable { setIntent(ScheduleIntent.ToggleTodayScheduleVisibility) }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ScheduleTabs(
    todayPersonalSchedules: List<Schedule>,
    todayGroupSchedules: List<Schedule>,
    setIntent: (ScheduleIntent) -> Unit
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
                setIntent = setIntent
            )
        }
    }
}

@Composable
private fun ScheduleTabsContent(
    page: Int,
    todayPersonalSchedules: List<Schedule>,
    todayGroupSchedules: List<Schedule>,
    setIntent: (ScheduleIntent) -> Unit
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
                        setIntent(ScheduleIntent.ShowAddScheduleDialog)
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
    scheduleList: List<Schedule>,
) {
    val sortedScheduleList = remember(scheduleList) { scheduleList.sortedBy { it.startTime } }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = sortedScheduleList.size,
            key = { index -> sortedScheduleList[index].scheduleId }
        ) { index ->
            sortedScheduleList[index].let {
                PersonalScheduleCard(schedule = it)
            }
            HorizontalDivider()
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScheduleScreenPreview() {
    PlanDingTheme {
        ScheduleScreen(
            uiState = ScheduleUIState()
        )
    }
}