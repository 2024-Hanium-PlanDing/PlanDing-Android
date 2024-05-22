package com.comst.domain.model.groupRoom

data class GroupRoomCreateResponseModel(
    val id: Long,
    val name: String,
    val description: String,
    val code: String,
    val ownerCode: String
)
