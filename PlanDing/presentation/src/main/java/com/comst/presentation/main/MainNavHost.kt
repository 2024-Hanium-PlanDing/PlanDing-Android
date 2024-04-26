package com.comst.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.comst.presentation.R
import com.comst.presentation.main.chat.ChatScreen
import com.comst.presentation.main.personal_schedule.PersonalScheduleScreen
import com.comst.presentation.main.group.GroupScreen
import com.comst.presentation.main.mypage.MyPageScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    Surface {
        Scaffold(
            content = { padding ->
                NavHost(
                    modifier = Modifier.padding(padding),
                    navController = navController,
                    startDestination = MainRoute.PERSONAL_SCHEDULE.route
                ){
                    composable(route = MainRoute.PERSONAL_SCHEDULE.route){
                        PersonalScheduleScreen()
                    }
                    composable(route = MainRoute.MY_PAGE.route){
                        MyPageScreen()
                    }
                    composable(route = MainRoute.CHAT.route){
                        ChatScreen()
                    }

                    composable(route = MainRoute.GROUP.route){
                        GroupScreen()
                    }

                }
            },

            bottomBar = {
                MainBottomBar(navController = navController)
            }
        )
    }
}