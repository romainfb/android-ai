package com.supdevinci.aieaie.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.aieaie.model.OpenAiMessageBody
import com.supdevinci.aieaie.model.request.BodyToSend
import com.supdevinci.aieaie.model.response.GeneratedAnswer
import com.supdevinci.aieaie.repository.OpenAiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OpenAiViewModel : ViewModel() {
    private val repository = OpenAiRepository()
    val openAiResponse: MutableStateFlow<GeneratedAnswer>? = null

    fun fetchMessages() {
        viewModelScope.launch {
            try {
                // Mettre votre textes ici
                val bodyToSend = BodyToSend(messages = listOf(
                    OpenAiMessageBody(role= "assistant", content= "test test")
                ))
                openAiResponse?.value = repository.getChatFromOpenAi(bodyToSend)

                Log.e("Fetch Messages List : ", openAiResponse?.value.toString())
            } catch (e: Exception) {
                // Handle error
                Log.e("Fetch Contact List : ", e.message.toString())
            }
        }
    }
}