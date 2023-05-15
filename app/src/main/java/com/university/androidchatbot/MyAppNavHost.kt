package com.university.androidchatbot

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.university.androidchatbot.auth.LoginScreen
import com.university.androidchatbot.auth.RegisterScreen
import com.university.androidchatbot.core.ui.UserPreferencesViewModel
import com.university.androidchatbot.screen.HomeScreen


const val loginRoute = "auth"
const val registerRoute = "register"

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()
    val userPreferencesViewModel = hiltViewModel<UserPreferencesViewModel>()
    val userPreferencesUiState = userPreferencesViewModel.uiState
    val myAppViewModel = hiltViewModel<MyAppViewModel>()
    val chatId = myAppViewModel.chatId

    NavHost(
        navController = navController,
        startDestination = loginRoute
    ) {
        composable(route = loginRoute)
        {
            LoginScreen(
                onClose = {
                    Log.d("MyAppNavHost", "navigate to list")
                    navController.navigate("chat/${chatId.value}")
                },
                navController = navController
            )
        }
        composable(route = registerRoute)
        {
            RegisterScreen(
                onClose = {
                    Log.d("MyAppNavHost", "navigate to list")
                    navController.navigate("chat/${chatId.value}")
                },
            )
        }
        composable(route = "chat/{chatId}", arguments = listOf(navArgument("chatId") { type = NavType.IntType }))
        {
            HomeScreen(chatId.value, navigate = { chatId ->
                myAppViewModel.chatId.value = chatId
                navController.navigate("chat/$chatId") })
        }
    }

    LaunchedEffect(userPreferencesUiState.token) {
        if (userPreferencesUiState.token.isNotEmpty()) {
            Log.d("MyAppNavHost", "Launched effect navigate to items "+ userPreferencesUiState.token)
            myAppViewModel.setToken(userPreferencesUiState.token)
            navController.navigate("chat/${chatId.value}") {
                popUpTo(0)
            }
        }
    }
}


@Composable
fun Home() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.onSurface
    ) {
        Text(text = "Hello!")
    }
}