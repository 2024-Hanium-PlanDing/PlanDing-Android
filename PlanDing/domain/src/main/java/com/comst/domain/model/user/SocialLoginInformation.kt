package com.comst.domain.model.user

data class SocialLoginInformation(
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
