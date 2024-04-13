package com.comst.data.model.user

import kotlinx.serialization.Serializable


@Serializable
data class SocialLoginParam (
    val profileNickname: String,
    val accountEmail: String,
    val profileImage: String,
    val socialId: String,
)