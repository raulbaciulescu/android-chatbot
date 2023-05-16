package com.university.androidchatbot

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.university.androidchatbot.screen.LoginScreen
import com.university.androidchatbot.screen.RegisterScreen
import com.university.androidchatbot.viewmodel.UserPreferencesViewModel
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