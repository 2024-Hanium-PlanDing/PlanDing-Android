package com.comst.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.comst.presentation.R

enum class MainRoute(
    val route: String,
    val contentDescription: String,
    val iconResource: Int,
){
    PERSONAL_SCHEDULE("PersonalScheduleScreen", "나의 일정", iconResource = R.drawable.ic_main_schedule),
    GROUP("GroupScheduleScreen", "그룹", iconResource = R.drawable.ic_main_group),
    CHAT("ChatScreen", "채팅", iconResource = R.drawable.ic_main_chat),
    MY_PAGE("MyPageScreen", "내 정보", iconResource = R.drawable.ic_main_mypage);

    @Composable
    fun iconPainter(): Painter {
        iconResource.let { return painterResource(id = it) }
    }
}