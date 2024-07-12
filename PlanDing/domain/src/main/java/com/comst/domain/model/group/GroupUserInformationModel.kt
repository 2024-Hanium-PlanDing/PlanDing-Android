package com.comst.domain.model.group

data class GroupUserInformationModel (
    val id: Long,
    val userCode: String,
    val userName: String,
    val email: String,
    val profileImageUrl: String,
    val hasPermission: Boolean
)