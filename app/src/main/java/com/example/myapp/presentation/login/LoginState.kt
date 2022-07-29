package com.example.myapp.presentation.login

import com.example.myapp.presentation.UiText

data class LoginState(
    //When user opens the screen, this is the state that reflects what he should see first
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,

)
