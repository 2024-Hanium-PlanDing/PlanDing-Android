package com.comst.data.model.group

import kotlinx.serialization.Serializable

@Serializable
data class GroupCreateParam(
    val name : String,
    val description : String
)
