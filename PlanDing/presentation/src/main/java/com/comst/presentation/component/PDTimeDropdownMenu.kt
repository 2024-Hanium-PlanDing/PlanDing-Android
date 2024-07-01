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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comst.presentation.R

@Composable
fun PDTimeDropdownMenu() {
    val hours = (6..24).toList()

    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var selectTime by remember { mutableIntStateOf(6) }
    val listState = rememberLazyListState()

    LaunchedEffect(isDropDownMenuExpanded) {
        if (isDropDownMenuExpanded) {
            listState.scrollToItem(selectTime - 6)
        }
    }

    Column(
        modifier = Modifier.width(90.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .clickable { isDropDownMenuExpanded = !isDropDownMenuExpanded }
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = String.format("%02d시", selectTime), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = if (isDropDownMenuExpanded) R.drawable.ic_keyboard_arrow_up_24 else R.drawable.ic_keyboard_arrow_down_24
                    ),
                    contentDescription = "TimeDropdown"
                )
            }
        }

        if (isDropDownMenuExpanded) {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                    .heightIn(max = 200.dp)
                    .fillMaxWidth()
            ) {
                LazyColumn(state = listState) {
                    items(hours.size) { index ->
                        val hour = hours[index]
                        DropdownMenuItem(
                            text = { Text(text = String.format("%02d시", hour)) },
                            onClick = {
                                isDropDownMenuExpanded = false
                                selectTime = hour
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PDTimeDropdownMenuPreview() {
    MaterialTheme {
        PDTimeDropdownMenu()
    }
}