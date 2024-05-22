package com.comst.data.model.user

import com.comst.domain.model.user.LoginResponseModel

data class LoginResponseDTO (
    val userCode:String,
    val accessToken:String,
    val refreshToken:String
)

fun LoginResponseDTO.toDomainModel(): LoginResponseModel {
    return LoginResponseModel(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}