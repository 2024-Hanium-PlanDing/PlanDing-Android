package com.comst.presentation.model.group.socket

data class SendChatDTO(
    val groupCode: String,
    val content: String,
    val sender: String,
    val profileImage: String,
    val name: String,
    val type: String,
)
