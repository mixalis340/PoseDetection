package com.example.myapp.presentation.login
//Each event corresponds to a single thing a user can do on the screen(user interaction)
sealed class LoginEvent {
    data class EmailChanged(val email: String): LoginEvent()
    data class PasswordChanged(val password: String): LoginEvent()

    object Submit: LoginEvent()

}
