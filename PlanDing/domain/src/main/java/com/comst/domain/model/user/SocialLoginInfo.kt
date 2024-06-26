package com.comst.domain.model.user

data class SocialLoginInfo(
    val profileNickname: String,
    val accountEmail: String,
    val profileImage: String,
    val socialId: String,
    val type: Type
) {
    enum class Type {
        KAKAO, GOOGLE
    }
}
