package com.comst.domain.model.user

data class LoginResponseModel (
    val userCode:String,
    val accessToken:String,
    val refreshToken:String
)