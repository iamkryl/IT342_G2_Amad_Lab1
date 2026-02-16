package com.example.mobileapplication.models

data class User(
    val user_id: Long,
    val first_name: String,
    val last_name: String,
    val email: String,
    val created_at: String
)