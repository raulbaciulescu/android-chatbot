package com.university.androidchatbot.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.university.androidchatbot.R
import com.university.androidchatbot.loginRoute
import com.university.androidchatbot.registerRoute
import com.university.androidchatbot.screen.TAG
import com.university.androidchatbot.viewmodel.LoginViewModel


@Composable
fun RegisterScreen(onClose: () -> Unit, navController: NavController) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val loginUiState = loginViewModel.uiState
    val passwordVisibility = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            //Image(painter = image, contentDescription = null, modifier = Modifier.size(300.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                .padding(10.dp)
        ) {
            var firstName by remember { mutableStateOf("") }
            TextField(
                label = { Text(text = "First Name") },
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            var lastName by remember { mutableStateOf("") }
            TextField(
                label = { Text(text = "Last Name") },
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            var email by remember { mutableStateOf("") }
            TextField(
                label = { Text(text = "Email") },
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            var password by remember { mutableStateOf("") }
            TextField(
                label = { Text(text = "Password") },
                value = password,
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = if (passwordVisibility.value) R.drawable.eye1 else R.drawable.eye2),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    }
                },
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            )

            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = { loginViewModel.register(firstName, lastName, email, password) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(text = "Register", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.padding(20.dp))
            Text(
                text = "Login Instead",
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(loginRoute)
                })
            )
            Spacer(modifier = Modifier.padding(20.dp))
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