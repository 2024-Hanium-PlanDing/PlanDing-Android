package com.comst.data.model.chat

import com.comst.domain.model.chat.ChatMessageModel

data class ChatMessageResponseDTO(
    val id: Long,
    val userCode: String,
    val name: String,
    val profileImage: String,
    val message: String,
    val createdAt: String,
)
fun ChatMessageResponseDTO.toDomainModel(): ChatMessageModel {
    return ChatMessageModel(
        id = id,
        userCode = userCode,
        name = name,
        profileImage = profileImage,
        message = message,
        createdAt = createdAt
    )
}
