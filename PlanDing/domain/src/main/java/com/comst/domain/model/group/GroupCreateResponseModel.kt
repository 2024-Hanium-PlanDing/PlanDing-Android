package com.comst.domain.model.group

data class GroupCreateResponseModel(
    val id: Long,
    val name: String,
    val description: String,
    val code: String,
    val ownerCode: String,
    val thumbnailPath: String
)
