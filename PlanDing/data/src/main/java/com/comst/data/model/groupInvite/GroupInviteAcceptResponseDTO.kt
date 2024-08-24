package com.comst.data.model.groupInvite

import com.comst.domain.model.groupInvite.GroupInviteAcceptResponseModel

data class GroupInviteAcceptResponseDTO (
    val groupCode: String
)

fun GroupInviteAcceptResponseDTO.toDomainModel(): GroupInviteAcceptResponseModel{
    return GroupInviteAcceptResponseModel(
        groupCode = groupCode
    )
}