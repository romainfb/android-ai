package com.supdevinci.aieaie.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.supdevinci.aieaie.model.response.GeneratedAnswer
import com.supdevinci.aieaie.ui.theme.AIEAIETheme
import com.supdevinci.aieaie.viewmodel.OpenAiViewModel

class MainActivity : ComponentActivity() {
    private val openAiViewModel = OpenAiViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openAiViewModel.fetchMessages()
        setContent {
            AIEAIETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MessageScreen(openAiViewModel)
                }
            }
        }
    }
}


@Composable
fun MessageScreen(viewModel: OpenAiViewModel) {
    val messagesList by viewModel.openAiResponse.collectAsState()

    println("TEST TEST ${messagesList}")
    Column {
        if (messagesList == null) {
            Text(text = "Loading...")
        } else {
            MessagesItemList(
                messagesList = messagesList!!
            )
        }
    }
}

@Composable
fun MessagesItemList(
    messagesList: GeneratedAnswer
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(messagesList.choices) { message ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "${message.message.role}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "${message.message.content}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}