package com.comst.domain.usecase.chat

import com.comst.domain.model.chat.ChatMessageModel
import com.comst.domain.repository.ChatRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class GetChatMessageListUseCase  @Inject constructor(
    private val chatRepository: ChatRepository
){
    suspend operator fun invoke(
        groupCode: String,
    ): ApiResult<List<ChatMessageModel>> {
        return chatRepository.getChatMessageList(
            groupCode = groupCode
        )
    }
}