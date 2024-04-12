package com.supdevinci.aieaie.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.supdevinci.aieaie.entity.MessageEntity
import com.supdevinci.aieaie.ui.theme.AIEAIETheme
import com.supdevinci.aieaie.ui.theme.Black
import com.supdevinci.aieaie.ui.theme.Gradient
import com.supdevinci.aieaie.viewmodel.DiscussionActivityViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val conversationId = intent.getLongExtra("conversationId", 0L)
        val conversationViewModel = DiscussionActivityViewModel(applicationContext, conversationId)


        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }


        setContent {
            AIEAIETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Black
                ) {
                    MessageScreen(conversationViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(conversationViewModel: DiscussionActivityViewModel) {
    val messagesList = conversationViewModel.conversationMessages
    val context = LocalContext.current

    Column {

        Spacer(modifier = Modifier.height(22.dp))

        Box(modifier = Modifier
            .background(Color.Transparent)
        ) {

            Button(
                onClick = {
                    val intent = Intent(context, ListActivity::class.java)
                    ContextCompat.startActivity(context, intent, null)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "‹",
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = Gradient
                        ),
                    ),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                    fontSize = 40.sp,
                )
            }

        }

        Box(
            Modifier
                .weight(0.8f)
                .fillMaxSize()
        ) {
            MessagesItemList(messagesList = messagesList)
        }

        var text by remember { mutableStateOf(TextFieldValue("")) }

        Box(
            Modifier
                .weight(0.12f)
                .fillMaxWidth()
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Écrivez votre message")  },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray,
                    disabledContainerColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    errorTextColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .heightIn(min = 56.dp), // Hauteur minimale
                singleLine = false // Permettre plusieurs lignes
            )
        }

        // Ajouter un bouton pour envoyer le message
        Button(
            onClick = {
                val newMessage = text.text
                if (newMessage.isNotBlank() && newMessage.isNotEmpty()) {
                    text = TextFieldValue("")
                    conversationViewModel.sendMessage(newMessage)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Brush.horizontalGradient(Gradient))

        ) {
            Text(text = "Envoyer")
        }
    }
}

@Composable
fun MessagesItemList(messagesList: SnapshotStateList<MessageEntity>) {
    val gradientColors = listOf(Color.Red, Color.Magenta, Color.Cyan)
    LazyColumn(reverseLayout = true) {
        items(messagesList.reversed()) { message ->
            if (message.role != "system") {
                Column(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = if (message.role == "user") Arrangement.End else Arrangement.Start
                    ) {
                        if (message.role != "user") {
                            // Cercle pour la photo de profil
                            Box(
                                Modifier
                                    .size(48.dp) // Taille totale, y compris la bordure
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            gradientColors, // Couleurs du dégradé
                                        )
                                    )
                            ) {
                                // Boîte pour le cercle de la photo de profil
                                Box(
                                    Modifier
                                        .align(Alignment.Center)
                                        .size(40.dp) // Taille du cercle
                                        .clip(CircleShape)
                                        .background(Color.Gray)
                                ) {
                                    ProfilePicture(url = "https://cdn.discordapp.com/attachments/290407097747898368/1227228742666158171/418180d6c2bb13e60083d187e8baf9bb.jpg?ex=6627a4d5&is=66152fd5&hm=081bc8445c58c7fe1dccfe527d29b902ab217922551a08cf3b78214dd78396fd&")
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp)) // Espace entre la photo de profil et le texte du message
                        }

                        // Texte du message
                        Box(
                            Modifier
                                .weight(1f)
                                .clip(
                                    if (message.role == "assistant") RoundedCornerShape(
                                        20.dp,
                                        20.dp,
                                        20.dp,
                                        0.dp
                                    ) else RoundedCornerShape(20.dp, 20.dp, 0.dp, 20.dp)
                                )
                                .run {
                                    if (message.role == "assistant") {
                                        this.background(Brush.horizontalGradient(gradientColors))
                                    } else {
                                        this.background(Color.DarkGray)
                                    }
                                }
                        ) {
                            Text(
                                text = message.message,
                                Modifier.padding(16.dp),
                                fontSize = 16.sp,
                                color = if (message.role == "assistant") Color.Black else Color.White
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ProfilePicture(url: String, modifier: Modifier = Modifier, shape: Shape = CircleShape) {
    val context = LocalContext.current

    Box(
        modifier = modifier.size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.size(40.dp),
            factory = { contextFactory ->
                ImageView(contextFactory).apply {
                    Glide.with(context)
                        .load(url)
                        .apply(RequestOptions().centerCrop())
                        .into(this)
                }
            }
        )
    }
}
