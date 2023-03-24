package com.kotlineering.ksoc.client.android.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import com.kotlineering.ksoc.client.Greeting
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navigator: KsocNavigator,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Text("${Greeting().greet()} - UserId: ${viewModel.userId}")
            Text("")
            ClickableText(text = AnnotatedString("Log Out"), onClick = { viewModel.logout() })
        }
    }
}
