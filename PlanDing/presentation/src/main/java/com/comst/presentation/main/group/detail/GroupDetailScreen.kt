package com.comst.presentation.main.group.detail

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.comst.domain.model.base.Schedule
import com.comst.domain.model.chat.ChatMessageModel
import com.comst.domain.model.group.GroupUserInformationModel
import com.comst.domain.util.DateUtils
import com.comst.presentation.R
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.component.PDButton
import com.comst.presentation.component.PDCalendarBottomSheet
import com.comst.presentation.component.PDGroupScheduleCard
import com.comst.presentation.component.PDScheduleBarChart
import com.comst.presentation.main.group.detail.GroupDetailContract.*
import com.comst.presentation.main.group.detail.addGroupMember.AddGroupMemberDialog
import com.comst.presentation.main.group.detail.addSchedule.AddGroupScheduleDialog
import com.comst.presentation.model.group.GroupProfileUIModel
import com.comst.presentation.ui.theme.BackgroundColor3
import com.comst.presentation.ui.theme.Blue400
import com.comst.presentation.ui.theme.MainPurple400
import com.comst.presentation.ui.theme.PlanDingTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
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

    val handleEffect: (GroupDetailSideEffect) -> Unit = { effect ->

    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        GroupDetailScreen(
            uiState = uiState,
            setIntent = viewModel::setIntent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GroupDetailScreen(
    uiState: GroupDetailUIState,
    setIntent: (GroupDetailIntent) -> Unit = {}
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
                    ModalDrawerSheet(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        drawerShape = RectangleShape
                    ) {
                        GroupDrawerContent(
                            userCode = uiState.userCode,
                            groupMemberList = uiState.groupMember,
                            setIntent
                        )
                    }
                }

            },
            drawerState = drawerState,
            gesturesEnabled = true,
            modifier = Modifier.fillMaxSize()
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = uiState.groupProfile.name) },
                            navigationIcon = {
                                IconButton(onClick = {  }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "favorite")
                                }
                            },
                            actions = {
                                Row {
                                    IconButton(onClick = {  }) {
                                        Icon(
                                            painterResource(
                                                id = if (uiState.groupProfile.isAlarm) R.drawable.ic_notifications_on_24 else R.drawable.ic_notifications_off_24
                                            ),
                                            tint = MaterialTheme.colorScheme.primary,
                                            contentDescription = "notification"
                                        )
                                    }
                                    IconButton(onClick = {  }) {
                                        Icon(
                                            painterResource(
                                                id = if (uiState.groupProfile.isFavorite) R.drawable.ic_favorite_on_24 else R.drawable.ic_favorite_off_24
                                            ),
                                            tint = MaterialTheme.colorScheme.primary,
                                            contentDescription = "favorite"
                                        )
                                    }
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                }

                            }
                        )
                    },
                    bottomBar = {
                        if (uiState.currentPage == 1) {
                            MessageInputBar(
                                message = uiState.chat,
                                setIntent = setIntent
                            )
                        }
                    },
                    content = { paddingValues ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            item { GroupProfile(uiState.groupProfile) }
                            item {
                                GroupTabs(
                                    selectWeekGroupScheduleList = uiState.selectWeekGroupScheduleOriginalList.toList() + uiState.newScheduleList.toList(),
                                    selectUIDate = uiState.selectUIDate,
                                    selectDay = uiState.selectDay,
                                    selectedWeekdays = uiState.selectedWeekdays,
                                    isChartView = uiState.isBarChartView,
                                    selectedDayIndex = uiState.selectedDayIndex,
                                    currentPage = uiState.currentPage,
                                    userCode = uiState.userCode,
                                    chatList = uiState.chatOriginalList.toList() + uiState.newChatList.toList(),
                                    setIntent = setIntent,
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    if (uiState.isBottomSheetVisible) {
        PDCalendarBottomSheet(
            date = DateUtils.uiDateToDate(uiState.selectUIDate),
            onCloseBottomSheet = {
                setIntent(GroupDetailIntent.CloseBottomSheetClick)
            },
            onDateSelected = { date ->
                setIntent(GroupDetailIntent.SelectDate(date))
            }
        )
    }

    if (uiState.isAddScheduleDialogVisible) {
        AddGroupScheduleDialog(
            groupProfile = uiState.groupProfile,
            date = uiState.selectLocalDate,
            onDismiss = {
                setIntent(GroupDetailIntent.HideAddScheduleDialog)
            },
            onConfirm = { schedule ->
                setIntent(GroupDetailIntent.CreateSchedule(schedule))
                setIntent(GroupDetailIntent.HideAddScheduleDialog)
            }
        )
    }

    if (uiState.isAddGroupMemberDialogVisible) {
        AddGroupMemberDialog(
            groupCode = uiState.groupProfile.groupCode,
            onDismiss = {
                setIntent(GroupDetailIntent.HideAddGroupMemberDialog)
            }
        )
    }
}

@Composable
fun MessageInputBar(
    message: String,
    setIntent: (GroupDetailIntent) -> Unit
) {
    val componentHeight = 56.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(componentHeight)
            .background(BackgroundColor3),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = message,
            onValueChange = { newChat ->
                setIntent(
                    GroupDetailIntent.ChatChange(newChat)
                )
            },
            modifier = Modifier
                .weight(1f)
                .height(componentHeight),
            placeholder = { Text("Type a message") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = BackgroundColor3,
                unfocusedContainerColor = BackgroundColor3,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        )
        Box(
            modifier = Modifier
                .size(componentHeight)
                .clickable {
                    setIntent(
                        GroupDetailIntent.SendChat
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_send_24),
                contentDescription = "Send",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun GroupProfile(groupProfile: GroupProfileUIModel) {
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
                    PDButton(
                        text = if (groupProfile.isGroupAdmin) "프로필 수정" else "그룹 탈퇴",
                        onClick = {}
                    )
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
    selectWeekGroupScheduleList: List<Schedule>,
    selectedWeekdays: List<String>,
    selectUIDate: String,
    selectDay: String,
    isChartView: Boolean,
    selectedDayIndex: Int,
    currentPage: Int,
    userCode: String,
    chatList: List<ChatMessageModel>,
    setIntent: (GroupDetailIntent) -> Unit
) {
    val pages = listOf("그룹 일정", "그룹 채팅")
    val pagerState = rememberPagerState(initialPage = currentPage)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            setIntent(GroupDetailIntent.ChangePage(page))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BoxWithConstraints {
            val tabWidth = maxWidth / pages.size

            TabRow(
                selectedTabIndex = currentPage,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[currentPage])
                            .width(tabWidth)
                            .height(4.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                },
            ) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        selected = currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                setIntent(GroupDetailIntent.ChangePage(index))
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
            GroupTabsContent(
                page = page,
                selectUIDate = selectUIDate,
                selectDay = selectDay,
                selectWeekGroupScheduleList = selectWeekGroupScheduleList,
                selectedWeekdays = selectedWeekdays,
                isChartView = isChartView,
                selectedDayIndex = selectedDayIndex,
                userCode =  userCode,
                chatList =  chatList,
                setIntent = setIntent,
            )
        }
    }
}

@Composable
private fun GroupTabsContent(
    page: Int,
    selectUIDate: String,
    selectDay: String,
    selectWeekGroupScheduleList: List<Schedule>,
    selectedWeekdays: List<String>,
    isChartView: Boolean,
    selectedDayIndex: Int,
    userCode: String,
    chatList: List<ChatMessageModel>,
    setIntent: (GroupDetailIntent) -> Unit
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
                    ScheduleHeaderRow(
                        selectUIDate = selectUIDate,
                        selectDay = selectDay,
                        isChartView = isChartView,
                        setIntent = setIntent
                    )
                    if (isChartView) {
                        PDScheduleBarChart(
                            scheduleList = selectWeekGroupScheduleList,
                            days = selectedWeekdays
                        )
                    } else {
                        GroupScheduleList(
                            selectedDayIndex = selectedDayIndex,
                            selectedWeekdays = selectedWeekdays,
                            selectWeekGroupScheduleList = selectWeekGroupScheduleList,
                            setIntent = setIntent
                        )
                    }
                }
            }

            1 -> {
                ChatList(
                    userCode = userCode,
                    chatList = chatList
                )
            }
        }
    }
}

@Composable
fun ScheduleHeaderRow(
    selectUIDate: String,
    selectDay: String,
    isChartView: Boolean,
    setIntent: (GroupDetailIntent) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DateSelectTab(
            modifier = Modifier.weight(1f),
            selectUIDate = selectUIDate,
            selectDay = selectDay,
            setIntent = setIntent
        )

        Spacer(modifier = Modifier.width(8.dp))


        Box(
            modifier = Modifier
                .size(40.dp)
                .border(1.dp, MainPurple400, shape = RoundedCornerShape(8.dp))
                .clickable { setIntent(GroupDetailIntent.ToggleView) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = if (isChartView) R.drawable.ic_table_chart_24 else R.drawable.ic_bar_chart_24),
                contentDescription = "Toggle View",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(40.dp)
                .border(1.dp, MainPurple400, shape = RoundedCornerShape(8.dp))
                .clickable { setIntent(GroupDetailIntent.ShowAddScheduleDialog) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_schedule_add_24),
                contentDescription = "Add Schedule",
                tint = MaterialTheme.colorScheme.primary
            )
        }

    }
}

@Composable
fun GroupScheduleList(
    selectedDayIndex: Int,
    selectedWeekdays: List<String>,
    selectWeekGroupScheduleList: List<Schedule>,
    setIntent: (GroupDetailIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(selectedWeekdays.size) { index ->
                Text(
                    text = selectedWeekdays[index],
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clickable { setIntent(GroupDetailIntent.SelectDay(index)) },
                    textAlign = TextAlign.Center,
                    fontWeight = if (index == selectedDayIndex) FontWeight.Bold else FontWeight.Normal,
                    color = if (index == selectedDayIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.6f
                    )
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(800.dp)
        ) {
            items(
                count = selectWeekGroupScheduleList.filter { it.day == selectedWeekdays[selectedDayIndex] }.size,
                key = { index -> selectWeekGroupScheduleList.filter { it.day == selectedWeekdays[selectedDayIndex] }[index].scheduleId }
            ) { index ->
                val schedule =
                    selectWeekGroupScheduleList.filter { it.day == selectedWeekdays[selectedDayIndex] }[index]
                PDGroupScheduleCard(
                    schedule = schedule,
                    onShowScheduleDetail = { schedule ->
                        setIntent(GroupDetailIntent.ShowScheduleDetailBottomSheet(schedule))
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun DateSelectTab(
    modifier: Modifier,
    selectUIDate: String,
    selectDay: String,
    setIntent: (GroupDetailIntent) -> Unit
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .background(
                shape = RoundedCornerShape(8.dp),
                color = BackgroundColor3
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { setIntent(GroupDetailIntent.OpenBottomSheetClick) },
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

@Composable
private fun ChatList(
    userCode: String,
    chatList: List<ChatMessageModel>
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var shouldScroll by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(800.dp),
        state = listState,
        reverseLayout = true
    ) {
        items(
            count = chatList.size,
            key = { index -> chatList[index].id }
        ) { index ->
            val chat = chatList[chatList.size - 1 - index]
            if (chat.userCode == userCode) {
                MyChatCard(chat = chat)
            } else {
                OtherChatCard(chat = chat)
            }
        }
    }

    LaunchedEffect(chatList.size) {
        val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        shouldScroll = lastVisibleItemIndex == 0

        if (shouldScroll) {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        }
    }
}

@Composable
private fun GroupDrawerContent(
    userCode: String,
    groupMemberList: List<GroupUserInformationModel>,
    setIntent: (GroupDetailIntent) -> Unit
){
    val sortedGroupMemberList = groupMemberList
        .sortedWith(
            compareByDescending<GroupUserInformationModel> { it.userCode == userCode }
                .thenByDescending { it.hasPermission }
                .thenBy { it.userName }
        )

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "그룹 서랍",
            modifier = Modifier.padding(vertical = 16.dp),
            fontWeight = FontWeight.Bold
        )

        HorizontalDivider()

        Text(
            text = "그룹원",
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )

        AddGroupMember(setIntent)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(
                count = sortedGroupMemberList.size,
                key = { index -> sortedGroupMemberList[index].userCode }
            ) { index ->
                val member =  sortedGroupMemberList[index]
                if (member.userCode == userCode){
                    MyGroupMemberCard(user = member)
                } else {
                    OtherGroupMemberCard(user = member)
                }
            }
        }

    }
}

@Composable
fun AddGroupMember(
    showAddGroupMemberDialog: (GroupDetailIntent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showAddGroupMemberDialog(GroupDetailIntent.ShowAddGroupMemberDialog)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.ic_add_24),
                colorFilter = ColorFilter.tint(Blue400),
                contentDescription = "그룹원 추가",
                contentScale = ContentScale.Crop,
                )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "그룹원 초대",
            color = Blue400
        )
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GroupDetailPreview() {
    PlanDingTheme {
        GroupDetailScreen(
            uiState = GroupDetailUIState()
        )
    }
}