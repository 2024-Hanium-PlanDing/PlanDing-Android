package com.comst.data.model.user

import com.comst.domain.model.user.LoginResponseModel

data class LoginResponseDTO (
    val userCode:String,
    val accessToken:String,
    val refreshToken:String
)

fun LoginResponseDTO.toDomainModel(): LoginResponseModel {
    return LoginResponseModel(
        userCode = userCode,
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}