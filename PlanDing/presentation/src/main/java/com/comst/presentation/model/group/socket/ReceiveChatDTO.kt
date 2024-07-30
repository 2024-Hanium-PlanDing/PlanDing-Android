package com.comst.presentation.model.group.socket

import com.comst.domain.model.chat.ChatMessageModel

data class ReceiveChatDTO(
    val id: Long,
    val userCode: String,
    val name: String,
    val profileImage: String,
    val message: String,
    val createdAt: String,
)
fun ReceiveChatDTO.toDomainModel(): ChatMessageModel {
    return ChatMessageModel(
        id = id,
        userCode = userCode,
        name = name,
        profileImage = profileImage,
        message = message,
        createdAt = createdAt
    )
}