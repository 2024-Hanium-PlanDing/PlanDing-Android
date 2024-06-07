package com.comst.presentation.main.personal_schedule

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.comst.presentation.main.group.GroupCard
import com.comst.presentation.main.group.GroupUIAction
import com.comst.presentation.ui.theme.PlanDingTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalScheduleScreen(
    viewModel: PersonalScheduleViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is PersonalScheduleSideEffect.Toast -> Toast.makeText(
                context,
                sideEffect.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val calendarBottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    PersonalScheduleScreen(
        selectUIDate = state.selectUIDate,
        selectDay = state.selectDay,
        selectedWeekdays = state.selectedWeekdays,
        isTodayScheduleVisible = state.isTodayScheduleVisible,
        todayScheduleEvents = state.todayScheduleEvents,
        selectWeekScheduleEvents = state.selectWeekScheduleEvents,
        onUIAction = viewModel::onUIAction
    )

    if (state.isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onUIAction(PersonalScheduleUIAction.CloseBottomSheet)
            },
            sheetState = calendarBottomSheetState,
        ) {
            PDCalendar(viewModel = viewModel)
        }
    }
}

@Composable
private fun PersonalScheduleScreen(
    selectUIDate: String,
    selectDay: String,
    selectedWeekdays: List<String>,
    isTodayScheduleVisible: Boolean,
    todayScheduleEvents: List<ScheduleEvent>,
    selectWeekScheduleEvents: List<ScheduleEvent>,
    onUIAction: (PersonalScheduleUIAction) -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            PDScreenHeader(text = "스케줄")

            SelectedDate(
                selectDate = selectUIDate,
                selectDay = selectDay,
                isTodayScheduleVisible = isTodayScheduleVisible,
                todayScheduleEvents = todayScheduleEvents,
                onUIAction = onUIAction
            )


            PDScheduleChart(events = selectWeekScheduleEvents, days = selectedWeekdays)
        }
    }
}

@Composable
private fun SelectedDate(
    selectDate: String,
    selectDay: String,
    todayScheduleEvents: List<ScheduleEvent>,
    isTodayScheduleVisible: Boolean,
    onUIAction: (PersonalScheduleUIAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(8.dp),
                color = Color.White
            ),
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onUIAction(PersonalScheduleUIAction.OpenBottomSheet)
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
                text = "$selectDate $selectDay",
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = if (isTodayScheduleVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "오늘의 스케줄 토글",
                modifier = Modifier.clickable {
                    onUIAction(PersonalScheduleUIAction.ToggleTodayScheduleVisibility)
                }
            )

        }

        if (isTodayScheduleVisible) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(
                        color = Color(0xFFF7F8FA),
                        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    )
            ) {
                if (todayScheduleEvents.isEmpty()) {
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
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            count = todayScheduleEvents.size,
                            key = { index -> todayScheduleEvents[index].scheduleId }
                        ) { index ->
                            todayScheduleEvents[index].let {
                                PersonalScheduleCard(schedule = it)
                            }
                            Divider()
                        }
                    }
                }
                FloatingActionButton(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(16.dp)
                        .align(Alignment.BottomEnd),
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(60.dp),
                    onClick = {
                        onUIAction(PersonalScheduleUIAction.AddTodaySchedule)
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PersonalScheduleScreenPreview() {
    PlanDingTheme {
        PersonalScheduleScreen(

        )
    }
}