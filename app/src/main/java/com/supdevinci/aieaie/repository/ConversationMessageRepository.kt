package com.supdevinci.aieaie.repository

import androidx.annotation.WorkerThread
import com.supdevinci.aieaie.dao.OpenAIDao
import com.supdevinci.aieaie.entity.MessageEntity

class ConversationMessageRepository(private val openAiDao: OpenAIDao) {
        @WorkerThread
        fun getMessages(conversationId: Long) = openAiDao.getMessages(conversationId)

        @WorkerThread
        suspend fun insertMessage(brainMessageEntity: MessageEntity) = openAiDao.insertMessage(brainMessageEntity)
}


