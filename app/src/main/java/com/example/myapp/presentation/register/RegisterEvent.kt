package com.example.myapp.presentation.register
//Each event corresponds to a single thing a user can do on the screen(user interaction)

sealed class RegisterEvent {
    data class EmailChanged(val email: String): RegisterEvent()
    data class UsernameChanged(val username: String): RegisterEvent()
    data class PasswordChanged(val password: String): RegisterEvent()

    object Submit: RegisterEvent()
}
