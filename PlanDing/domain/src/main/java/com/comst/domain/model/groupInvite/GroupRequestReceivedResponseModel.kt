package com.comst.domain.model.groupInvite

import com.comst.domain.model.group.GroupCardModel

data class GroupRequestReceivedResponseModel(
    val inviteCode: String,
    val groupCode: String,
    val groupName: String,
    val invitedUserCode: String,
    val userName: String,
    val groupResponse: GroupCardModel,
    val createdAt: String,
)