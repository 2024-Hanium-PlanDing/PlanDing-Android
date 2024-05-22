package com.comst.domain.model.user

data class LoginResponseModel (
    val accessToken:String,
    val refreshToken:String
)