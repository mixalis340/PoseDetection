package com.example.myapp.data.request

data class CreateAccountRequest(
    val email: String,
    val username: String,
    val password: String
)
