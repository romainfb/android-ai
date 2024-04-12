package com.supdevinci.aieaie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.supdevinci.aieaie.entity.ConversationEntity
import com.supdevinci.aieaie.entity.MessageEntity

@Dao
interface OpenAIDao {
    @Query("SELECT * FROM conversations ORDER BY conversationId DESC")
    fun getConversations(): List<ConversationEntity>

    @Query("DELETE FROM conversations")
    suspend fun deleteAllConversations()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(ConversationEntity: ConversationEntity): Long

    @Query("SELECT * FROM conversations WHERE conversationId = :conversationId")
    fun getConversationById(conversationId: Long): ConversationEntity

    @Query("UPDATE conversations SET conversationName = :conversationName WHERE conversationId = :conversationId")
    suspend fun updateConversationName(conversationId: Long, conversationName: String)

    @Query("DELETE FROM conversations WHERE conversationId = :conversationId")
    suspend fun deleteConversationById(conversationId: Long)

    @Query("SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY messageId")
    fun getMessages(conversationId: Long): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(MessageEntity: MessageEntity): Long

    @Query("DELETE FROM messages WHERE conversationId = :conversationId")
    suspend fun deleteMessagesByConversationId(conversationId: Long)

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()
}