package com.university.androidchatbot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.university.androidchatbot.R
import com.university.androidchatbot.ui.theme.AndroidChatbotTheme

@Composable
internal fun SplashScreen() =
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App logo",
            contentScale = ContentScale.FillWidth
        )
    }

@Preview
@Composable
private fun Preview_SplashScreen() {
    AndroidChatbotTheme {
        SplashScreen()
    }
}