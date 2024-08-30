package com.comst.presentation.model.group

import android.os.Parcelable
import com.comst.domain.model.group.GroupCardModel
import com.comst.domain.model.group.GroupCreateResponseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupCardUIModel(
    val groupId:Long,
    val groupCode:String,
    val groupOwnerCode:String,
    val groupName:String,
    val groupImageUrl:String,
    val groupDescription:String
): Parcelable

fun GroupCreateResponseModel.toGroupCardUIModel(): GroupCardUIModel {
    return GroupCardUIModel(
        groupId = id,
        groupCode = code,
        groupOwnerCode = ownerCode,
        groupName = name,
        groupImageUrl = thumbnailPath,
        groupDescription = description
    )
}

fun GroupCardModel.toGroupCardUIModel(): GroupCardUIModel {
    return GroupCardUIModel(
        groupId = groupId,
        groupCode = groupCode,
        groupOwnerCode = groupOwnerCode,
        groupName = groupName,
        groupImageUrl = groupImageUrl,
        groupDescription = groupDescription
    )
}