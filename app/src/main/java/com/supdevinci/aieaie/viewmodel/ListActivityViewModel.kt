package com.supdevinci.aieaie.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.aieaie.database.OpenAiDatabase
import com.supdevinci.aieaie.entity.ConversationEntity
import com.supdevinci.aieaie.repository.ConversationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListActivityViewModel(private val applicationContext: Context): ViewModel() {

    private lateinit var database: OpenAiDatabase
    private lateinit var repository: ConversationRepository

    val conversations = mutableStateListOf<ConversationEntity>()

    init {
        viewModelScope.launch {
            database = OpenAiDatabase.getDatabase(applicationContext)
            repository = ConversationRepository(database.openAIDAO())
        }
    }

    fun loadConversations() {
        viewModelScope.launch(Dispatchers.IO) {
            conversations.clear()
            conversations.addAll(repository.getConversations())
        }
    }

    fun insertConversation(conversationName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertConversation(ConversationEntity(0, conversationName))
            loadConversations()
        }
    }

    // Feature not implemented in the app, in coming versions

    fun deleteConversationById(conversationId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteConversationById(conversationId)
            repository.deleteMessagesByConversationId(conversationId)
            loadConversations()
        }
    }

    fun deleteAllConversations() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllConversations()
            repository.deleteAllMessages()
            loadConversations()
        }
    }
}