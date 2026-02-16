package com.example.mobileapplication.models

data class AuthResponse(
    val token: String,
    val email: String,
    val firstName: String,
    val lastName: String
)