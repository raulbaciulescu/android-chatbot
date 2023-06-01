package com.university.androidchatbot.feature.authentication.ui.register

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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.university.androidchatbot.ui.components.VerticalSpace
import com.university.androidchatbot.LOGIN_ROUTE
import com.university.androidchatbot.feature.authentication.ui.login.TAG


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onClose: () -> Unit, navController: NavController) {
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val registerUiState = registerViewModel.uiState
    val passwordVisibility = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(12.dp)
            .verticalScroll(scrollState)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        VerticalSpace(100.dp)
        Image(
            modifier = Modifier.fillMaxWidth(.6f),
            painter = painterResource(R.drawable.il_sign),
            contentDescription = null
        )
        Text(
            text = "Sign Up",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
            fontSize = 30.sp
        )
        VerticalSpace(20.dp)
        TextField(
            label = { Text(text = "First Name") },
            value = firstName,
            onValueChange = { firstName = it },
            modifier = Modifier.fillMaxWidth()
        )
        VerticalSpace(10.dp)
        TextField(
            label = { Text(text = "Last Name") },
            value = lastName,
            onValueChange = { lastName = it },
            modifier = Modifier.fillMaxWidth()
        )
        VerticalSpace(10.dp)
        TextField(
            label = { Text(text = "Email") },
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth()
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
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        )
        VerticalSpace(20.dp)
        Button(
            onClick = { registerViewModel.register(firstName, lastName, email, password) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(
                text = "Register",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        VerticalSpace(20.dp)
        Text(
            text = "Login Instead",
            modifier = Modifier.clickable(onClick = {
                navController.navigate(LOGIN_ROUTE)
            })
        )
        VerticalSpace(10.dp)
        if (registerUiState.isAuthenticating) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            );
        }
        if (registerUiState.authenticationError != null) {
            Text(text = "Login failed ${registerUiState.authenticationError}")
        }
    }

    LaunchedEffect(registerUiState.authenticationCompleted) {
        Log.d(TAG, "Auth completed");
        if (registerUiState.authenticationCompleted) {
            onClose();
        }
    }
}