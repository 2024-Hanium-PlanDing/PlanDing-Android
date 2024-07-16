package com.comst.presentation.main.group.detail

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.comst.domain.model.base.Schedule
import com.comst.domain.util.DateUtils
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDButton
import com.comst.presentation.component.PDCalendarBottomSheet
import com.comst.presentation.component.PDScheduleChart
import com.comst.presentation.main.group.detail.GroupDetailContract.GroupDetailIntent
import com.comst.presentation.main.group.detail.GroupDetailContract.GroupDetailSideEffect
import com.comst.presentation.model.group.GroupProfileUIModel
import com.comst.presentation.ui.theme.BackgroundColor2
import com.comst.presentation.ui.theme.PlanDingTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailScreen(
    groupCode: String,
    viewModel: GroupDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(groupCode) {
        viewModel.initialize(groupCode)
    }

    DisposableEffect(Unit) {

        onDispose {
            viewModel.cancelStomp()
        }
    }

    val context = LocalContext.current
    val handleEffect: (GroupDetailSideEffect) -> Unit = { effect ->
        when (effect) {
            else -> {}
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        val scrollState = rememberScrollState()

        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.clickable {
                        viewModel.postSchedule()
                    },
                    title = {
                        Text(text = uiState.groupProfile.name)
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* Handle back click */ }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                GroupProfile(uiState.groupProfile)
                GroupTabs(
                    selectWeekGroupScheduleEvents = uiState.selectWeekGroupScheduleEvents + uiState.newScheduleEvents,
                    selectUIDate = uiState.selectUIDate,
                    selectDay = uiState.selectDay,
                    selectedWeekdays = uiState.selectedWeekdays,
                    onUIAction = viewModel::setIntent
                )
            }

            if (uiState.isBottomSheetVisible) {
                PDCalendarBottomSheet(
                    date = DateUtils.uiDateToDate(uiState.selectUIDate),
                    onCloseBottomSheet = {
                        viewModel.setIntent(GroupDetailIntent.CloseBottomSheetClick)
                    },
                    onDateSelected = { date ->
                        viewModel.setIntent(GroupDetailIntent.SelectDate(date))
                    }
                )
            }
        }
    }
}

@Composable
fun GroupProfile(
    groupProfile: GroupProfileUIModel
) {
    var isDescriptionVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(groupProfile.thumbnailUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Column {
                    if (groupProfile.isGroupAdmin) {
                        PDButton(
                            text = "프로필 수정",
                            onClick = {}
                        )
                    } else {
                        PDButton(
                            text = "그룹 탈퇴",
                            onClick = {}
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = groupProfile.description,
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = if (isDescriptionVisible) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )

                Icon(
                    imageVector = if (isDescriptionVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isDescriptionVisible) "Hide Description" else "Show Description",
                    modifier = Modifier.clickable {
                        isDescriptionVisible = !isDescriptionVisible
                    }
                )
            }

            Text(
                text = groupProfile.createdBy,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                color = Color.Gray
            )

        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun GroupTabs(
    selectWeekGroupScheduleEvents: List<Schedule>,
    selectedWeekdays: List<String>,
    selectUIDate: String,
    selectDay: String,
    onUIAction: (GroupDetailIntent) -> Unit
) {
    val pages = listOf("그룹 일정", "그룹원")
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
    }

    HorizontalPager(
        count = pages.size,
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        GroupTabsContent(
            page = page,
            selectUIDate = selectUIDate ,
            selectDay = selectDay,
            selectWeekGroupScheduleEvents = selectWeekGroupScheduleEvents,
            selectedWeekdays = selectedWeekdays,
            onUIAction = onUIAction
        )
    }
}

@Composable
private fun GroupTabsContent(
    page: Int,
    selectUIDate: String,
    selectDay: String,
    selectWeekGroupScheduleEvents: List<Schedule>,
    selectedWeekdays: List<String>,
    onUIAction: (GroupDetailIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        when (page) {
            0 -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ScheduleHeader(
                        selectUIDate = selectUIDate,
                        selectDay = selectDay,
                        onUIAction = onUIAction
                    )

                    PDScheduleChart(
                        events = selectWeekGroupScheduleEvents,
                        days = selectedWeekdays
                    )
                }
            }

            1 -> {

            }
        }
    }
}

@Composable
private fun ScheduleHeader(
    selectUIDate: String,
    selectDay: String,
    onUIAction: (GroupDetailIntent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
            .padding(horizontal = 16.dp)
            .background(
                shape = RoundedCornerShape(8.dp),
                color = Color.White
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onUIAction(GroupDetailIntent.OpenBottomSheetClick) },
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
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GroupDetailPreview() {
    PlanDingTheme {
        GroupDetailScreen(groupCode = "6318", viewModel = hiltViewModel())
    }
}