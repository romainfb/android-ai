package com.supdevinci.aieaie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val messageId: Long,
    val conversationId: Long,
    val role: String,
    val message: String,
)