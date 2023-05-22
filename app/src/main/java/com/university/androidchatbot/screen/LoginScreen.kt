package com.university.androidchatbot.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.university.androidchatbot.R
import com.university.androidchatbot.registerRoute
import com.university.androidchatbot.viewmodel.LoginViewModel

val TAG = "LoginScreen"

@Composable
fun LoginScreen(onClose: () -> Unit, navController: NavController) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val loginUiState = loginViewModel.uiState
    //val image: Painter = painterResource(id = R.drawable.login_image)
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
            Text(
                text = "Sign In",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                ),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                var username by remember { mutableStateOf("raul@raul.com") }
                TextField(
                    label = { Text(text = "Username") },
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.padding(10.dp))
                var password by remember { mutableStateOf("raul") }
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

                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = { loginViewModel.login(username, password) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(text = "Sign In", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Create An Account",
                    modifier = Modifier.clickable(onClick = {
                        navController.navigate(registerRoute)
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
    }


//    Scaffold(
//        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.login)) }) },
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(24.dp)
//        ) {
//            var username by remember { mutableStateOf("") }
//            TextField(
//                label = { Text(text = "Username") },
//                value = username,
//                onValueChange = { username = it },
//                modifier = Modifier.fillMaxWidth()
//            )
//            var password by remember { mutableStateOf("") }
//            TextField(
//                label = { Text(text = "Password") },
//                visualTransformation = PasswordVisualTransformation(),
//                value = password,
//                onValueChange = { password = it },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Log.d(TAG, "recompose");
//            Button(onClick = {
//                Log.d(TAG, "login...");
//                loginViewModel.login(username, password)
//            }) {
//                Text("Login")
//            }
//            Button(onClick = {
//                navController.navigate(registerRoute)
//            }) {
//                Text("Register")
//            }
//            if (loginUiState.isAuthenticating) {
//                LinearProgressIndicator(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(15.dp)
//                );
//            }
//            if (loginUiState.authenticationError != null) {
//                Text(text = "Login failed ${loginUiState.authenticationError.message}")
//            }
//        }
//    }

    LaunchedEffect(loginUiState.authenticationCompleted) {
        Log.d(TAG, "Auth completed");
        if (loginUiState.authenticationCompleted) {
            onClose();
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen({})
//}