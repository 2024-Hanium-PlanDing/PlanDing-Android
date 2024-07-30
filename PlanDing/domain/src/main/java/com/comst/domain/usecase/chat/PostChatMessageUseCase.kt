package com.comst.domain.usecase.chat

import com.comst.domain.model.chat.ChatMessageModel
import com.comst.domain.repository.ChatRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class PostChatMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
){
    suspend operator fun invoke(
        groupCode: String,
        content:String
    ): ApiResult<ChatMessageModel>{
        return chatRepository.postChatMessage(
            groupCode = groupCode,
            content = content
        )
    }
}