package com.comst.domain.model.group

data class GroupInformationModel(
    val id: Long,
    val name: String,
    val groupCode: String,
    val description: String,
    val thumbnailUrl: String,
    val createdBy: String,
    val users: List<GroupUserInformationModel>,
    val isGroupAdmin: Boolean
)

