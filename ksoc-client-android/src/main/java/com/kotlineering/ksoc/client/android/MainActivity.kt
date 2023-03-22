package com.kotlineering.ksoc.client.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.kotlineering.ksoc.client.Greeting
import com.kotlineering.ksoc.client.auth.AuthRepository
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: Temporary
class MainActivityViewModel(
    val authRepository: AuthRepository
) : ViewModel()
class MainActivity : ComponentActivity() {

    // TODO: Temporary
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Temporary
        Log.d("TEST", "${viewModel.authRepository.tokens} ${viewModel.authRepository.user}")

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GreetingView(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
