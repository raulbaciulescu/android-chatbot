package com.university.androidchatbot.auth.data.remote

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
