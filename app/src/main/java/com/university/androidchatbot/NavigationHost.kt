package com.university.androidchatbot

import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.university.androidchatbot.feature.authentication.ui.login.LoginScreen
import com.university.androidchatbot.feature.authentication.ui.register.RegisterScreen
import com.university.androidchatbot.feature.chat.ChatScreen2
import com.university.androidchatbot.feature.drawer.DrawerViewModel
import com.university.androidchatbot.feature.splash.SplashScreen
import com.university.androidchatbot.utils.Util
import com.university.androidchatbot.feature.splash.SplashViewModel
import com.university.androidchatbot.ui.components.MainBody
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val SPLASH_ROUTE = "splash"
const val LOGIN_ROUTE = "auth"
const val REGISTER_ROUTE = "register"
const val HOME_ROUTE = "home"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost() {
    val navController = rememberNavController()

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
                onRegisterClick = {
                    navController.navigate(route = REGISTER_ROUTE)
                },
                onAuthenticationSuccessful = {
                    Log.d("MyAppNavHost", "navigate to list")
                    navController.navigate(HOME_ROUTE)
                }
            )
        }

        composable(route = REGISTER_ROUTE) {
            RegisterScreen(
                onLoginClick = navController::popBackStack,
                onAuthenticationSuccessful = {
                    Log.d("MyAppNavHost", "navigate to list")
                    navController.navigate(HOME_ROUTE)
                }
            )
        }

        composable(route = HOME_ROUTE) {
            val mainNavController: NavHostController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val coroutineScope = rememberCoroutineScope()
            val drawerViewModel: DrawerViewModel = hiltViewModel()

            MainBody(
                viewModel = drawerViewModel,
                drawerState = drawerState,
                onNewChatClick = {
                    Util.chatId = 0
                    mainNavController.navigate("${HOME_ROUTE}/chat/0")
                },
                onNewPdfChatClick = { path ->
                    Util.chatId = 0
                    Util.pdfPath = path
                    println("nav host " + Util.pdfPath)
                    mainNavController.navigate("${HOME_ROUTE}/chat/0")
                },
                onChatClick = { chatId ->
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    Util.chatId = chatId
                    mainNavController.navigate("${HOME_ROUTE}/chat/$chatId") {
                        popUpTo("${HOME_ROUTE}/chat/0")
                    }
                },
                onLogoutSuccessful = {
                    Util.chatId = 0
                    navController.navigate(LOGIN_ROUTE) {
                        popUpTo(HOME_ROUTE) {
                            inclusive = true
                        }
                    }
                },
            ) {
                NavHost(
                    navController = mainNavController,
                    startDestination = "${HOME_ROUTE}/chat/{chatId}",
                ) {
                    composable(
                        route = "home/chat/{chatId}",
                        arguments = listOf(navArgument("chatId") {
                            type = NavType.IntType
                            defaultValue = 0
                        })
                    ) {
                        ChatScreen2(
                            onChatDeleted = {
                                mainNavController.navigate("home/chat/0")
                            },
                            onMenuClick = {
                                coroutineScope.launch {
                                    drawerViewModel.refreshChats()
                                    drawerState.open()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}