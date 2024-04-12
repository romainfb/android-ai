package com.supdevinci.aieaie.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.supdevinci.aieaie.ui.theme.AIEAIETheme
import com.supdevinci.aieaie.ui.theme.Gradient
import com.supdevinci.aieaie.viewmodel.ListActivityViewModel

class ListActivity : ComponentActivity() {

    private lateinit var conversationListViewModel: ListActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        conversationListViewModel = ListActivityViewModel(applicationContext)
        conversationListViewModel.loadConversations()

        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        setContent {
            AIEAIETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    ListScreen(conversationListViewModel, Gradient)
                }
            }
        }
    }
}


@Composable
fun ListScreen(conversationListViewModel: ListActivityViewModel, gradientColors: List<Color>) {

    val imageUrl = "https://cdn.discordapp.com/attachments/1181877809329750078/1226827111885574204/418180d6c2bb13e60083d187e8baf9bb_2.png?ex=66262ec8&is=6613b9c8&hm=f1e40f8fbf228d444ed85ee9666e3edb3ba115526afe23d657b88e82ca21465b&"
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { contextFactory ->
            ImageView(contextFactory).apply {
                Glide.with(contextFactory)
                    .load(imageUrl)
                    .apply(RequestOptions().centerCrop())
                    .into(this)
            }
        }
    )

    Column(
        Modifier
            .fillMaxWidth()
            .padding(50.dp)) {

        Box(modifier = Modifier
            .background(Color.Transparent)
        ) {

            Button(
                onClick = {
                    val intent = Intent(context, WelcomeActivity::class.java)
                    startActivity(context, intent, null)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "‹",
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = gradientColors
                        ),
                    ),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                    fontSize = 40.sp,
                )
            }

        }

        Text(
            text = "Liste des discussions",
            Modifier.fillMaxWidth(),
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = Gradient
                ),
            ),
            fontSize = 38.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Light,
            textAlign = androidx.compose.ui.text.style.TextAlign.Left,
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(Brush.horizontalGradient(gradientColors))
                .padding(8.dp)
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(context, intent, null)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Nouvelle discussion", color = Color.Black, fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Normal)
            }


        }

        Column {
            Spacer(modifier = Modifier.padding(20.dp))

            ListScreenContent(conversationListViewModel, gradientColors)

        }

    }
}

@Composable
fun ListScreenContent(conversationListViewModel: ListActivityViewModel, gradientColors: List<Color>) {
    val context = LocalContext.current

    conversationListViewModel.conversations.forEach {
        Button(
            onClick = {
                context.startActivity(Intent(context, MainActivity::class.java).apply {
                    putExtra("conversationId", it.conversationId)
                })
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        ) {
            Text(
                text = it.conversationName,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                modifier = Modifier.fillMaxWidth().clip(
                    RoundedCornerShape(50.dp)
                ).background(Brush.horizontalGradient(gradientColors)).padding(10.dp),
            )
        }
    }


}