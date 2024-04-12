package com.supdevinci.aieaie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey(autoGenerate = true)
    val conversationId: Long,
    val conversationName: String,
)