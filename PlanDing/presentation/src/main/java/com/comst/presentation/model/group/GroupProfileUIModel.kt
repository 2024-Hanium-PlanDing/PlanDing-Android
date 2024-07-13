package com.comst.presentation.model.group

import com.comst.domain.model.group.GroupInformationModel

data class GroupProfileUIModel(
    val id: Long,
    val name: String,
    val groupCode: String,
    val description: String,
    val thumbnailUrl: String,
    val createdBy: String,
    val isGroupAdmin: Boolean
)

fun GroupInformationModel.toUIModel(): GroupProfileUIModel {
    return  GroupProfileUIModel(
        id = id,
        name = name,
        groupCode = groupCode,
        description = description,
        thumbnailUrl = thumbnailUrl,
        createdBy = createdBy,
        isGroupAdmin = isGroupAdmin
    )
}