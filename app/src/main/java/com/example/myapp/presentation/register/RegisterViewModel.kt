package com.example.myapp.presentation.register

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.Constants
import com.example.myapp.R
import com.example.myapp.Resource
import com.example.myapp.domain.use_case.RegisterUseCase
import com.example.myapp.domain.use_case.ValidationResult
import com.example.myapp.presentation.UiText
import com.example.myapp.presentation.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    var state by mutableStateOf(RegisterState())


    //It's purpose is to send events from the ViewModel to UI
    //private val validationEventChanel = Channel<ValidationEvent>()
    //val validationEvents = validationEventChanel.receiveAsFlow()
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    //Receives events from our screen
    fun onEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegisterEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegisterEvent.UsernameChanged -> {
                state = state.copy(username = event.username)
            }
            is RegisterEvent.Submit -> {
                submitData()
            }
        }
    }



    private fun submitData() {
        val emailResult = validateEmail(state.email)
        val passwordResult = validatePassword(state.password)
        val usernameResult = validateUsername(state.username)

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
            state = state.copy(isLoading = true)
            delay(2000L)
           val result = registerUseCase(
               email = state.email,
               username = state.username,
               password = state.password
           )
            when(result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(UiText.StringResource(R.string.success_registretion))
                    )
                    state = state.copy(isLoading = false)
                    state = state.copy(email = "")
                    state = state.copy(username = "")
                    state = state.copy(password = "")
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(result.uiText ?: UiText.StringResource(R.string.error_unkown))
                    )
                    state = state.copy(isLoading = false)
                }
            }
        }

    }


    private fun validateEmail(email: String): ValidationResult {
        val trimmedEmail = email.trim()
        if(trimmedEmail.isBlank()) {
            return  ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.this_field_cant_be_empty)
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_email)
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    private fun validateUsername(username: String): ValidationResult {
        val trimmedUsername = username.trim()
        if(trimmedUsername.isBlank()) {
            return  ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.this_field_cant_be_empty)
            )
        }
        if(trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.input_too_short,
                    Constants.MIN_USERNAME_LENGTH
                )
            )
        }
        return ValidationResult(
            successful = true,
        )
    }

    private fun validatePassword(password: String): ValidationResult {
        if(password.isBlank()) {
            return  ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.this_field_cant_be_empty)
            )
        }
        if(password.length < Constants.MIN_PASSWORD_LENGTH) {
            return  ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.input_too_short,
                    Constants.MIN_PASSWORD_LENGTH
                )
            )
        }
        val digitsInPassword = password.any { it.isDigit() }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        if(!digitsInPassword || !capitalLettersInPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_password)
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    sealed class UiEvent {
        data class SnackbarEvent(val uiText: UiText): UiEvent()
    }
}