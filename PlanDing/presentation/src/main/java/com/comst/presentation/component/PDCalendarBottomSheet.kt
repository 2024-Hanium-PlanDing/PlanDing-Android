package com.comst.presentation.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PDCalendarBottomSheet(
    date: Date,
    onCloseBottomSheet: () -> Unit,
    onDateSelected: (Date) -> Unit
) {
    val calendarBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            onCloseBottomSheet()
        },
        sheetState = calendarBottomSheetState,
    ) {
        PDCalendar(
            initialDate = date,
            onDateSelected = onDateSelected
        )
    }
}