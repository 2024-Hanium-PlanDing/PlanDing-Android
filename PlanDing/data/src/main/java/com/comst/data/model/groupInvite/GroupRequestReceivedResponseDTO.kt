package com.comst.data.model.groupInvite

import com.comst.data.model.group.MyGroupRoomDTO
import com.comst.data.model.group.toDomainModel
import com.comst.domain.model.group.GroupCardModel
import com.comst.domain.model.groupInvite.GroupRequestReceivedResponseModel

data class GroupRequestReceivedResponseDTO(
    val inviteCode: String,
    val groupCode: String,
    val groupName: String,
    val invitedUserCode: String,
    val userName: String,
    val groupResponse: MyGroupRoomDTO,
    val createdAt: String,
)

fun GroupRequestReceivedResponseDTO.toDomainModel(): GroupRequestReceivedResponseModel {
    return GroupRequestReceivedResponseModel(
        inviteCode = inviteCode,
        groupCode = groupCode,
        groupName = groupName,
        invitedUserCode = invitedUserCode,
        userName = userName,
        groupResponse = groupResponse.toDomainModel(),
        createdAt = createdAt

    )
}