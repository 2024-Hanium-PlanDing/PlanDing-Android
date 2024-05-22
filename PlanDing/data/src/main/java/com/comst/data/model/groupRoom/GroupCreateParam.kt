package com.comst.data.model.groupRoom

import kotlinx.serialization.Serializable

@Serializable
data class GroupCreateParam(
    val name : String,
    val description : String
)
