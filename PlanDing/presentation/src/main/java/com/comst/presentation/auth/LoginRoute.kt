package com.comst.presentation.auth

sealed class LoginRoute(
    val name : String
) {

    object LoginScreen : LoginRoute("LoginScreen")

    object SignUpScreen : LoginRoute("SignUpScreen")
}