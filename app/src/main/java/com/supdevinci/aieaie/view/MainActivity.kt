package com.supdevinci.aieaie.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.supdevinci.aieaie.model.IaModel
import com.supdevinci.aieaie.ui.theme.AIEAIETheme
import com.supdevinci.aieaie.viewmodel.MainActivityViewModel

class MainActivity : ComponentActivity() {
    private val mainActivityViewModel = MainActivityViewModel()

    private val iaModel1 = IaModel("Toto", 200 )
    private val iaModel2 = IaModel("LALA", 500 )
    private val iaModel3 = IaModel("IAAAAAAA", 400 )
    private val iaModel4 = IaModel("supdevinci", 300 )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AIEAIETheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
            mainActivityViewModel.checkResponseCode(200, iaModel1.responseCode)
            mainActivityViewModel.checkResponseCode(200, iaModel2.responseCode)
            mainActivityViewModel.checkResponseCode(200, iaModel3.responseCode)
            mainActivityViewModel.checkResponseCode(200, iaModel4.responseCode)
            showToast(mainActivityViewModel.concatText(iaModel1.responseMsg))
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    Button(onClick = { /*TODO*/ }) {
        
    }
}

@Composable
fun showToast(textToDisplay: String) {
    Toast.makeText(LocalContext.current, textToDisplay, Toast.LENGTH_LONG).show()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AIEAIETheme {
        Greeting("Android")
    }
}