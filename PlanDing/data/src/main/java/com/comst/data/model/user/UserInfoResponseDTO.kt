package com.comst.data.model.user

import com.comst.domain.model.UserInfo

data class UserInfoResponseDTO(
    val id: Long,
    val username: String,
    val email: String,
    val profileImage: String,
    val role: String,
    val userCode: String
)

fun UserInfoResponseDTO.toDomainModel(): UserInfo {
    return UserInfo(
        id = id,
        username = username,
        email = email,
        profileImage = profileImage,
        userCode = userCode
    )
}