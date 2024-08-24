package com.comst.presentation.model.mypage.groupRequestsReceived

import com.comst.domain.model.groupInvite.GroupRequestReceivedResponseModel

data class GroupRequestReceivedCardModel(
    val inviteCode: String,
    val groupCode: String,
    val groupName: String,
    val invitedUserCode: String,
    val invitedUserName: String,
    val groupImageUrl: String,
    val createdAt: String,
)

fun GroupRequestReceivedResponseModel.toGroupRequestReceivedCardModelUIModel(): GroupRequestReceivedCardModel{
    return GroupRequestReceivedCardModel(
        inviteCode = inviteCode,
        groupCode = groupCode,
        groupName = groupName,
        invitedUserCode = invitedUserCode,
        invitedUserName = userName,
        groupImageUrl = groupResponse.groupImageUrl,
        createdAt = createdAt

    )
}