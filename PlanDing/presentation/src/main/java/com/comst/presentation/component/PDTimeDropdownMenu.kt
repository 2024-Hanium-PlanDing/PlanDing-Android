package com.comst.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comst.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PDTimeDropdownMenu(
    modifier: Modifier = Modifier,
    selectedTime: Int,
    onConfirm: (Int) -> Unit
) {
    val hours = (6..24).toList()

    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var selectTime by remember { mutableIntStateOf(selectedTime) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val listState = rememberLazyListState()

    LaunchedEffect(isDropDownMenuExpanded) {
        if (isDropDownMenuExpanded) {
            listState.scrollToItem(selectTime - 6)
        }
    }

    ExposedDropdownMenuBox(
        expanded = isDropDownMenuExpanded,
        onExpandedChange = {
            isDropDownMenuExpanded = !isDropDownMenuExpanded
        },
        modifier = modifier
    ){
        OutlinedTextField(
            readOnly = true,
            value = String.format("%02d시", selectTime),
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isDropDownMenuExpanded
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
        ExposedDropdownMenu(
            expanded = isDropDownMenuExpanded,
            onDismissRequest = {
                isDropDownMenuExpanded = false
                focusManager.clearFocus()
            },
            modifier = Modifier
                .background(Color.White)
        ) {
            hours.forEach { hour ->
                val uiHour = String.format("%02d시", hour)
                DropdownMenuItem(
                    text = { Text(text = uiHour) },
                    onClick = {
                        selectTime = hour
                        isDropDownMenuExpanded = false
                        onConfirm(selectTime)
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.background(Color.White)
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PDTimeDropdownMenuPreview() {
    MaterialTheme {
        PDTimeDropdownMenu(selectedTime = 7366, onConfirm = {})
    }
}