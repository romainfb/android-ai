package com.supdevinci.aieaie.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.aieaie.database.OpenAiDatabase
import com.supdevinci.aieaie.entity.ConversationEntity
import com.supdevinci.aieaie.entity.MessageEntity
import com.supdevinci.aieaie.model.OpenAiMessageBody
import com.supdevinci.aieaie.model.request.BodyToSend
import com.supdevinci.aieaie.repository.ConversationMessageRepository
import com.supdevinci.aieaie.repository.ConversationRepository
import com.supdevinci.aieaie.repository.OpenAiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiscussionActivityViewModel(

    private val applicationContext: Context, var conversationId: Long) : ViewModel() {
    private lateinit var database: OpenAiDatabase
    private lateinit var conversationRepository: ConversationRepository
    private lateinit var messageRepository: ConversationMessageRepository
    private val openAiRepository = OpenAiRepository()

    val conversationEntity = mutableStateOf(ConversationEntity(0, ""))
    val conversationMessages = mutableStateListOf<MessageEntity>()

    var isFetching = mutableStateOf(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            database = OpenAiDatabase.getDatabase(applicationContext)

            conversationRepository = ConversationRepository(database.openAIDAO())

            if (conversationId == 0L)
                conversationId = conversationRepository.insertConversation(ConversationEntity(0, "Conversation"))

            conversationEntity.value = conversationRepository.getConversationById(conversationId)

            messageRepository = ConversationMessageRepository(database.openAIDAO())
            loadMessages()
        }
    }

    private fun loadMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            conversationMessages.clear()
            conversationMessages.addAll(messageRepository.getMessages(conversationId))
        }
    }

    fun fetchMessages() {
        isFetching.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val messages = conversationMessages.map {
                    OpenAiMessageBody(
                        role = it.role,
                        content = it.message
                    )

                }

                val bodyToSend = BodyToSend(messages = messages)
                val response = openAiRepository.getChatFromOpenAi(bodyToSend)
                val generatedAnswer = response.choices.first().message

                insertMessage(generatedAnswer.role, generatedAnswer.content)
                isFetching.value = false
            } catch (e: Exception) {
                Log.e("fetchMessages", e.message.toString())
            }
        }
    }

    fun insertMessage(role: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            messageRepository.insertMessage(MessageEntity(0, conversationId, role, message))
            loadMessages()
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val msg = MessageEntity(0, conversationId, "user", message)

            messageRepository.insertMessage(msg)
            conversationMessages.add(msg)
            fetchMessages()
        }
    }

    // Feature coming soon

    fun deleteConversation(next: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            conversationRepository.deleteConversationById(conversationId)
            conversationRepository.deleteMessagesByConversationId(conversationId)
            next()
        }
    }
}