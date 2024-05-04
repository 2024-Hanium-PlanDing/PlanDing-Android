package com.comst.presentation.main.mypage

enum class MyPageMenu(
    val menu: String,
    val menuDescription: String,
){
    USAGE_GUIDE("이용안내","이용안내"),
    ACCOUNT_SETTING("계정관리", "계정관리"),
    CONTACT("문의하기","문의하기"),
    NOTIFICATION_SETTING("알림설정","알림설정"),
    SETTING("환경설정","환경설정"),
    LOGOUT("로그아웃","로그아웃")
}