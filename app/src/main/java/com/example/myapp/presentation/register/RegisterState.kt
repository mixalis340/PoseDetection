package com.example.myapp.presentation.register

import com.example.myapp.presentation.UiText

data class RegisterState(
    //When user opens the screen, this is the state that reflects what he should see first
    val email: String = "",
    val emailError: UiText? = null,
    val username: String = "",
    val usernameError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
)
