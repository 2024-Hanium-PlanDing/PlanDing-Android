package com.comst.presentation.model.group.socket

data class ReceiveChatDTO(
    val message: String,
    val profileImage: String,
    val name: String,
    val createdAt: String,
)
