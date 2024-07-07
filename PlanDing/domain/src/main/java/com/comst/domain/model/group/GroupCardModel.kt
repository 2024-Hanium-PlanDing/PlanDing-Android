package com.comst.domain.model.group

data class GroupCardModel (
    val groupId:Long,
    val groupCode:String,
    val groupOwnerCode:String,
    val groupName:String,
    val groupImageUrl:String,
    val groupDescription:String
)