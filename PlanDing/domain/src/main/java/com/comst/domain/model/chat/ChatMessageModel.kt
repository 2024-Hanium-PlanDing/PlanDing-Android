package com.comst.domain.model.chat

data class ChatMessageModel(
    val id: Long,
    val userCode: String,
    val name: String,
    val profileImage: String,
    val message: String,
    val createdAt: String,
)
