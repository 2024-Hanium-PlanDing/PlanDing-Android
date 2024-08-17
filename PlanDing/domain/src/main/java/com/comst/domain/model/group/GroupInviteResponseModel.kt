package com.comst.domain.model.group

data class GroupInviteResponseModel(
    val inviteCode : String,
    val groupCode : String,
    val groupName : String,
    val invitedUserCode : String,
    val createdAt : String,
)
