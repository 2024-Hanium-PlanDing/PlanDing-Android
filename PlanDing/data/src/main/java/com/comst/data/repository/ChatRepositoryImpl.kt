package com.comst.data.repository

import com.comst.data.model.base.toDomainModel
import com.comst.data.model.chat.ChatMessageParam
import com.comst.data.model.chat.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.ChatService
import com.comst.domain.model.chat.ChatMessageModel
import com.comst.domain.repository.ChatRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatService: ChatService
) : ChatRepository {
    override suspend fun getChatMessageList(
        groupCode: String
    ): ApiResult<List<ChatMessageModel>> {
        return ApiHandler.handle(
            execute = { chatService.getGroupChatsApi(groupCode) },
            mapper = { response -> response.map { it.toDomainModel() } }
        )
    }

    override suspend fun postChatMessage(
        groupCode: String,
        content: String,
    ): ApiResult<ChatMessageModel> {
        return ApiHandler.handle(
            execute = {
                val request = ChatMessageParam(
                    content = content
                )
                chatService.postChatApi(
                    groupCode = groupCode,
                    requestBody = request
                )
            },
            mapper = { response -> response.toDomainModel() }
        )
    }
}