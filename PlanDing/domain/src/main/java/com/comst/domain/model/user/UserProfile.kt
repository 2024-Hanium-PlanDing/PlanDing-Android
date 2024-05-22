package com.comst.domain.model.user

data class UserProfile (
    val username: String,
    val email: String,
    val profileImage: String,
    val userCode: String,
    val groupFavorite: String,
    val groupRequest: String
)