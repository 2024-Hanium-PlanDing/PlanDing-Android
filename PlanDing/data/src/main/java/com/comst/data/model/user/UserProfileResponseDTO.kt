package com.comst.data.model.user

import com.comst.domain.model.UserProfile

data class UserProfileResponseDTO (
    val groupFavorite: Int,
    val groupRequest: Int
)

fun UserProfileResponseDTO.toDomainModel(): UserProfile {
    return UserProfile(
        groupFavorite = groupFavorite.toString(),
        groupRequest = groupRequest.toString()
    )
}