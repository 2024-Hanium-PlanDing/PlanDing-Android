package com.comst.domain.repository

import com.comst.domain.model.chat.ChatMessageModel
import com.comst.domain.util.ApiResult

interface ChatRepository {
    suspend fun getChatMessageList(
        groupCode: String
    ): ApiResult<List<ChatMessageModel>>

    suspend fun postChatMessage(
        groupCode: String,
        content: String,
    ): ApiResult<ChatMessageModel>
}