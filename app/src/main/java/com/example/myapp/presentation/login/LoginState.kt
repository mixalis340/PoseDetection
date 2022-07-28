package com.example.myapp.presentation.login

data class LoginState(
    //When user opens the screen, this is the state that reflects what he should see first
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,

)
