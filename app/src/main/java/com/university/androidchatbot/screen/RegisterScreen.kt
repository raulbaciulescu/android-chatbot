package com.university.androidchatbot.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.university.androidchatbot.R
import com.university.androidchatbot.screen.TAG
import com.university.androidchatbot.viewmodel.LoginViewModel


@Composable
fun RegisterScreen(onClose: () -> Unit) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val loginUiState = loginViewModel.uiState

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.login)) }) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            var firstName by remember { mutableStateOf("") }
            TextField(
                label = { Text(text = "First Name") },
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier.fillMaxWidth()
            )
            var lastName by remember { mutableStateOf("") }
            TextField(
                label = { Text(text = "Last Name") },
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier.fillMaxWidth()
            )
            var email by remember { mutableStateOf("") }
            TextField(
                label = { Text(text = "Email") },
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth()
            )
            var password by remember { mutableStateOf("") }
            TextField(
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth()
            )
            Log.d(TAG, "recompose");
            Button(onClick = {
                Log.d(TAG, "login...");
                loginViewModel.register(firstName, lastName, email, password)
            }) {
                Text("Login")
            }
            if (loginUiState.isAuthenticating) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                );
            }
            if (loginUiState.authenticationError != null) {
                Text(text = "Login failed ${loginUiState.authenticationError.message}")
            }
        }
    }

    LaunchedEffect(loginUiState.authenticationCompleted) {
        Log.d(TAG, "Auth completed");
        if (loginUiState.authenticationCompleted) {
            onClose();
        }
    }
}