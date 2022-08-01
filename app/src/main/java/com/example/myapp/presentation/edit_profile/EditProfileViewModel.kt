package com.example.myapp.presentation.edit_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.myapp.domain.use_case.ValidateEmail
import com.example.myapp.domain.use_case.ValidatePassword
import com.example.myapp.domain.use_case.ValidateUsername
import com.example.myapp.presentation.UiText
import com.example.myapp.presentation.register.RegisterEvent
import com.example.myapp.presentation.register.RegisterState
import com.example.myapp.presentation.register.RegisterViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel

class EditProfileViewModel(
    private  val validateEmail: ValidateEmail = ValidateEmail(),
    private  val validateUsername: ValidateUsername = ValidateUsername(),
    private  val validatePassword: ValidatePassword = ValidatePassword()
): ViewModel() {
    var state by mutableStateOf(EditProfileState())

    //It's purpose is to send events from the ViewModel to UI
    private val validationEventChanel = Channel<ValidationEvent>()
    val validationEvents = validationEventChanel.receiveAsFlow()


    //Receives events from our screen
    fun onEvent(event: EditProfileEvent) {
        when(event) {
            is EditProfileEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is EditProfileEvent.UsernameChanged -> {
                state = state.copy(username = event.username)
            }
            is EditProfileEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val usernameResult = validateUsername.execute(state.username)

        val hasError = listOf(
            emailResult,
            passwordResult,
            usernameResult
        ).any { !it.successful}

        state = state.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            usernameError = usernameResult.errorMessage
        )
        if(hasError)
            return

        viewModelScope.launch {
            validationEventChanel.send(EditProfileViewModel.ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}