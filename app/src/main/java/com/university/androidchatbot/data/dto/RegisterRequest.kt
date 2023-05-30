package com.university.androidchatbot.data.dto

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
