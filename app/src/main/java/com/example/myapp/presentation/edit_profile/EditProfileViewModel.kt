package com.example.myapp.presentation.edit_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.myapp.domain.use_case.ValidateEmail
import com.example.myapp.domain.use_case.ValidatePassword
import com.example.myapp.domain.use_case.ValidateUsername
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel

class EditProfileViewModel(
    private  val validateEmail: ValidateEmail = ValidateEmail(),
    private  val validateUsername: ValidateUsername = ValidateUsername(),
    //private  val validateBio: ValidateBio = ValidateBio()
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
            is EditProfileEvent.BioChanged -> {
                state = state.copy(bio = event.bio)
            }
            is EditProfileEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val usernameResult = validateUsername.execute(state.username)

        val hasError = listOf(
            emailResult,
            //passwordResult,
            usernameResult
        ).any { !it.successful}

        state = state.copy(
            emailError = emailResult.errorMessage,
            //passwordError = passwordResult.errorMessage,
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