package com.university.androidchatbot

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.university.androidchatbot.auth.LoginScreen
import com.university.androidchatbot.core.data.Api
import com.university.androidchatbot.core.data.TokenInterceptor
import com.university.androidchatbot.core.ui.UserPreferencesViewModel
import javax.inject.Inject


val itemsRoute = "items"
val authRoute = "auth"

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()
    val onCloseItem = {
        Log.d("MyAppNavHost", "navigate back to list")
        navController.popBackStack()
    }
    val userPreferencesViewModel = hiltViewModel<UserPreferencesViewModel>()
    val userPreferencesUiState = userPreferencesViewModel.uiState
    val myAppViewModel = hiltViewModel<MyAppViewModel>()


    NavHost(
        navController = navController,
        startDestination = authRoute
    ) {
        composable(route = authRoute)
        {
            LoginScreen(
                onClose = {
                    Log.d("MyAppNavHost", "navigate to list")
                    navController.navigate(itemsRoute)
                }
            )
        }
    }

    LaunchedEffect(userPreferencesUiState.token) {
        if (userPreferencesUiState.token.isNotEmpty()) {
            Log.d("MyAppNavHost", "Lauched effect navigate to items")
            //Api.tokenInterceptor.token = userPreferencesUiState.token
            myAppViewModel.setToken(userPreferencesUiState.token)
            navController.navigate(itemsRoute) {
                popUpTo(0)
            }
        }
    }
}
