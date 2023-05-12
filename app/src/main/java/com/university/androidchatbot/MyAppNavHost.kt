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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.university.androidchatbot.auth.LoginScreen
import com.university.androidchatbot.auth.RegisterScreen
import com.university.androidchatbot.core.data.Api
import com.university.androidchatbot.core.data.TokenInterceptor
import com.university.androidchatbot.core.ui.UserPreferencesViewModel
import javax.inject.Inject


val loginRoute = "auth"
val registerRoute = "register"
val homeRoute = "home"

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
        startDestination = loginRoute
    ) {
        composable(route = loginRoute)
        {
            LoginScreen(
                onClose = {
                    Log.d("MyAppNavHost", "navigate to list")
                    navController.navigate(homeRoute)
                },
                navController = navController
            )
        }
        composable(route = registerRoute)
        {
            RegisterScreen(
                onClose = {
                    Log.d("MyAppNavHost", "navigate to list")
                    navController.navigate(homeRoute)
                },
            )
        }
        composable(route = homeRoute)
        {
            Home()
        }
    }

    LaunchedEffect(userPreferencesUiState.token) {
        if (userPreferencesUiState.token.isNotEmpty()) {
            Log.d("MyAppNavHost", "Launched effect navigate to items")
            //Api.tokenInterceptor.token = userPreferencesUiState.token
            myAppViewModel.setToken(userPreferencesUiState.token)
            navController.navigate(homeRoute) {
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