package com.supdevinci.aieaie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.supdevinci.aieaie.dao.OpenAIDao
import com.supdevinci.aieaie.entity.ConversationEntity
import com.supdevinci.aieaie.entity.MessageEntity

@Database(entities = [ConversationEntity::class, MessageEntity::class], version = 1)
abstract class OpenAiDatabase : RoomDatabase() {
    abstract fun openAIDAO(): OpenAIDao

    companion object {
        @Volatile
        private var INSTANCE: OpenAiDatabase? = null

        fun getDatabase(context: Context): OpenAiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OpenAiDatabase::class.java,
                    "_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}