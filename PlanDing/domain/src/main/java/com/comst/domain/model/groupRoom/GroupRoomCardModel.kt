package com.comst.domain.model.groupRoom

data class GroupRoomCardModel (
    val groupId:Long,
    val groupCode:String,
    val groupOwnerCode:String,
    val groupName:String,
    val groupImageUrl:String,
    val groupDescription:String
)