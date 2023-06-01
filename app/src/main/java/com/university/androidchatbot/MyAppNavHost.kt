package com.university.androidchatbot

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.university.androidchatbot.feature.chat.ui.HomeScreen
import com.university.androidchatbot.feature.authentication.ui.login.LoginScreen
import com.university.androidchatbot.feature.authentication.ui.register.RegisterScreen
import com.university.androidchatbot.feature.chat.ui.ChatViewModel
import com.university.androidchatbot.feature.splash.ui.SplashScreen
import com.university.androidchatbot.utils.Util
import com.university.androidchatbot.viewmodel.MyAppViewModel
import com.university.androidchatbot.feature.splash.ui.SplashViewModel
import kotlinx.coroutines.delay

const val LOGIN_ROUTE = "auth"
const val REGISTER_ROUTE = "register"
val HOME_ROUTE = "chat/0"
private const val SPLASH_ROUTE = "splash"

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()
    val myAppViewModel: MyAppViewModel = hiltViewModel()
    val chatViewModel: ChatViewModel = hiltViewModel()
    val chatId = Util.chatId

    NavHost(
        navController = navController,
        startDestination = SPLASH_ROUTE
    ) {
        composable(route = SPLASH_ROUTE) {
            val viewModel: SplashViewModel = hiltViewModel()
            val startDestination by viewModel.targetDestination.collectAsStateWithLifecycle()

            LaunchedEffect(startDestination) {
                startDestination?.let {
                    delay(1000)
                    navController.navigate(it) {
                        popUpTo(SPLASH_ROUTE) { inclusive = true }
                    }
                }
            }

            SplashScreen()
        }
        composable(route = LOGIN_ROUTE) {
            LoginScreen(
                onClose = {
                    Log.d("MyAppNavHost", "navigate to list")
                    navController.navigate("chat/${chatId}")
                },
                navController = navController
            )
        }
        composable(route = REGISTER_ROUTE) {
            RegisterScreen(
                onClose = {
                    Log.d("MyAppNavHost", "navigate to list")
                    navController.navigate("chat/${chatId}")
                },
                navController = navController
            )
        }
        composable(
            route = "chat/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.IntType })
        ) {
            HomeScreen(
                chatId = chatId,
                navigate = { chatId ->
                    Util.chatId = chatId
                    chatViewModel.selectChat(chatId)
                    navController.navigate("chat/$chatId")
                },
                onNewChatClick = {
                    Util.chatId = 0
                    navController.navigate("chat/0")
                },
                onNewChatWithPdfClick = { path ->
                    Util.chatId = 0
                    Util.pdfPath = path
                    println("nav host " + Util.pdfPath)
                    navController.navigate("chat/0")
                },
                onLogoutClick = {
                    myAppViewModel.logout()
                    Util.chatId = 0
                    navController.navigate(LOGIN_ROUTE)
                },
                chatViewModel = chatViewModel
            )
        }
    }
}