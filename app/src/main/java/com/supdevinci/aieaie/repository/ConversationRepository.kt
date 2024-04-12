package com.supdevinci.aieaie.repository

import androidx.annotation.WorkerThread
import com.supdevinci.aieaie.dao.OpenAIDao
import com.supdevinci.aieaie.entity.ConversationEntity
import com.supdevinci.aieaie.model.request.BodyToSend
import com.supdevinci.aieaie.model.response.GeneratedAnswer
import com.supdevinci.aieaie.service.RetrofitInstance

class ConversationRepository(private val openAiDao: OpenAIDao) {
        @WorkerThread
        fun getConversations() = openAiDao.getConversations()

        @WorkerThread
        suspend fun deleteAllConversations() = openAiDao.deleteAllConversations()

        @WorkerThread
        suspend fun insertConversation(conversationEntity: ConversationEntity) = openAiDao.insertConversation(conversationEntity)

        @WorkerThread
        fun getConversationById(conversationId: Long) = openAiDao.getConversationById(conversationId)

        @WorkerThread
        suspend fun deleteConversationById(conversationId: Long) = openAiDao.deleteConversationById(conversationId)

        @WorkerThread
        suspend fun deleteMessagesByConversationId(conversationId: Long) = openAiDao.deleteMessagesByConversationId(conversationId)

        @WorkerThread
        suspend fun deleteAllMessages() = openAiDao.deleteAllMessages()
}


