package com.comst.data.model.user

import com.comst.domain.model.user.UserProfile

data class UserProfileResponseDTO(
    val username: String,
    val email: String,
    val profileImage: String,
    val role: String,
    val userCode: String,
    val groupFavorite: Int,
    val groupRequest: Int
)

fun UserProfileResponseDTO.toDomainModel(): UserProfile {
    return UserProfile(
        username = username,
        email = email,
        profileImage = profileImage,
        userCode = userCode,
        groupFavorite = groupFavorite.toString(),
        groupRequest = groupRequest.toString()
    )
}