package com.comst.presentation.main.group.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CreateGroupNavHost(
    onFinish:()->Unit
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val sharedViewModel:CreateGroupViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = CreateGroupRoute.IMAGE_SELECT_SCREEN.route,
    ) {

        composable(
            route = CreateGroupRoute.IMAGE_SELECT_SCREEN.route
        ){
            ImageSelectScreen(
                viewModel = sharedViewModel,
                onBackClick = onFinish,
                onNextClick = {
                    navController.navigate(CreateGroupRoute.GROUP_DATA_SCREEN.route)
                }
            )
        }

        composable(
            route = CreateGroupRoute.GROUP_DATA_SCREEN.route
        ){
            CreateGroupScreen(
                viewModel = sharedViewModel,
                onBackClick = {
                    navController.navigateUp()
                },
                onFinish = onFinish
            )
        }
    }
}