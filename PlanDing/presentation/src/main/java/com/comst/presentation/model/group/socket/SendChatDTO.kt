package com.comst.presentation.model.group.socket

data class SendChatDTO(
    val content: String,
    val sender: String,
    val type: String,
)
