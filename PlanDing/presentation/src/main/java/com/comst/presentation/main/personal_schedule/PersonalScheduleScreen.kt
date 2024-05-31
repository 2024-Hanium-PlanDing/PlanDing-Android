package com.comst.presentation.main.personal_schedule

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.presentation.component.PDCalendar
import com.comst.presentation.component.PDScheduleChart
import com.comst.presentation.component.PDScreenHeader
import com.comst.presentation.model.ScheduleEvent
import com.comst.presentation.ui.theme.PlanDingTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.LocalDate

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
        scheduleEvent = state.scheduleEvent,
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
    scheduleEvent: List<ScheduleEvent>,
    onUIAction: (PersonalScheduleUIAction) -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .verticalScroll(rememberScrollState())
        ) {
            PDScreenHeader(text = "스케줄")

            SelectedDate(
                selectDate = selectUIDate,
                selectDay = selectDay,
                onUIAction = onUIAction
            )


            PDScheduleChart(events = scheduleEvent, days = selectedWeekdays)
        }
    }
}

@Composable
private fun SelectedDate(
    selectDate: String,
    selectDay: String,
    onUIAction: (PersonalScheduleUIAction) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .background(
                shape = RoundedCornerShape(8.dp),
                color = Color.White
            )
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