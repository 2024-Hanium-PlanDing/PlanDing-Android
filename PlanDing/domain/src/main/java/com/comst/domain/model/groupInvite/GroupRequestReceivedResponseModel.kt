package com.comst.domain.model.groupInvite

data class GroupRequestReceivedResponseModel(
    val inviteCode: String,
    val groupCode: String,
    val groupName: String,
    val invitedUserCode: String,
    val createdAt: String,
)