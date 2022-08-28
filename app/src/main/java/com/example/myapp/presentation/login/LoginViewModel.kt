package com.example.myapp.presentation.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.R
import com.example.myapp.Resource
import com.example.myapp.SimpleResource
import com.example.myapp.domain.use_case.*
import com.example.myapp.presentation.UiText
import com.example.myapp.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//The job of ViewModel is make use of use Cases, which contain the business logic(validation logic)
//And map the result to compose state

@HiltViewModel
class  LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authenticateUseCase: AuthenticateUseCase
): ViewModel() {
    var state by mutableStateOf(LoginState())

    //It's purpose is to send events from the ViewModel to UI
    //private val validationEventChanel = Channel<ValidationEvent>()
    //val validationEvents = validationEventChanel.receiveAsFlow()
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private val resultChannel = Channel<SimpleResource>()
    val authResults = resultChannel.receiveAsFlow()

    init {
      authenticate()
    }
    //Receives events from our screen
    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is LoginEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is LoginEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail(state.email)
        val passwordResult = validatePassword(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful}

        state = state.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage
        )
        if(hasError)
            return

        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = loginUseCase(
                email = state.email,
                password = state.password
            )
            state = state.copy(isLoading = false)
            when(result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.MainScreen.route)
                    )
                    state = state.copy(isLoading = false)
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(
                            result.uiText ?: UiText.StringResource(R.string.error_unkown)
                        )
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

    private fun validatePassword(password: String): ValidationResult {
        if(password.isBlank()) {
            return  ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.this_field_cant_be_empty)
            )
        }

        return ValidationResult(
            successful = true
        )
    }
    private fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            delay(1000L)
            val result = authenticateUseCase()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    sealed class UiEvent {
        data class SnackbarEvent(val uiText: UiText): UiEvent()
        data class Navigate(val route: String): UiEvent()
    }
}