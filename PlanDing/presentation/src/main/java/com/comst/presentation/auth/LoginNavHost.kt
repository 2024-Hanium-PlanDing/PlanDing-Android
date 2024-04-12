package com.comst.presentation.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions

@Composable
fun LoginNavHost(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginRoute.LoginScreen.name,
    ){

        composable(route = LoginRoute.LoginScreen.name){
            LoginScreen(
                onNavigateToSignUpScreen = {
                    navController.navigate(LoginRoute.SignUpScreen.name)
                }
            )
        }

        composable(route = LoginRoute.SignUpScreen.name) {
            SignUpScreen(
                onNavigationLoginScreen = {
                    navController.navigate(route = LoginRoute.LoginScreen.name) {
                        popUpTo(LoginRoute.LoginScreen.name) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}