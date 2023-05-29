package com.university.androidchatbot.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.university.androidchatbot.R
import com.university.androidchatbot.components.VerticalSpace
import com.university.androidchatbot.registerRoute
import com.university.androidchatbot.viewmodel.LoginViewModel

val TAG = "LoginScreen"


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onClose: () -> Unit,
    navController: NavController
) {
    val loginUiState = loginViewModel.uiState
    val passwordVisibility = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var username by remember { mutableStateOf("raul@raul.com") }
    var password by remember { mutableStateOf("raul") }

    Column(
        modifier = Modifier
            .padding(12.dp)
            .verticalScroll(scrollState)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        VerticalSpace(150.dp)
        Image(
            modifier = Modifier.fillMaxWidth(.6f),
            painter = painterResource(R.drawable.il_sign),
            contentDescription = null
        )
        Text(
            text = "Sign In",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
            fontSize = 30.sp
        )
        VerticalSpace(20.dp)
        TextField(
            label = { Text(text = "Username") },
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth(),
        )
        VerticalSpace(10.dp)
        TextField(
            label = { Text(text = "Password") },
            value = password,
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(
                            id = if (passwordVisibility.value)
                                R.drawable.ic_eye_open else R.drawable.ic_eye_closed
                        ),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            },
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisibility.value)
                VisualTransformation.None else PasswordVisualTransformation(),
        )
        VerticalSpace(50.dp)
        Button(
            onClick = { loginViewModel.login(username, password) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(text = "Sign In", fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)
        }
        VerticalSpace(20.dp)
        Text(
            text = "Create An Account",
            modifier = Modifier.clickable(onClick = {
                navController.navigate(registerRoute)
            })
        )
        VerticalSpace(20.dp)
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

    LaunchedEffect(loginUiState.authenticationCompleted) {
        Log.d(TAG, "Auth completed");
        if (loginUiState.authenticationCompleted) {
            onClose();
        }
    }
}