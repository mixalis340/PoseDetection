package com.example.myapp.presentation.edit_profile

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.myapp.presentation.UiText
import com.example.myapp.presentation.register.RegisterEvent
import com.example.myapp.presentation.register.RegisterState
import com.example.myapp.presentation.register.RegisterViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.example.myapp.Constants
import com.example.myapp.R
import com.example.myapp.Resource
import com.example.myapp.domain.use_case.*
import com.example.myapp.presentation.login.LoginViewModel
import com.example.myapp.presentation.profile.ProfileState
import com.example.myapp.presentation.profile.ProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            getProfile(userId)
        }
    }

    var state by mutableStateOf(EditProfileState())
    var profileState by mutableStateOf(ProfileState())

    //It's purpose is to send events from the ViewModel to UI
    private val _eventFlow = MutableSharedFlow<EditProfileViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun getProfile(userId: String) {
        profileState = profileState.copy(isLoading = true)
        viewModelScope.launch {
            val result = profileUseCases.getProfile(userId)
            when(result) {
                is Resource.Success -> {
                    val profile = result.data ?: kotlin.run {
                        _eventFlow.emit(UiEvent.SnackbarEvent(
                            UiText.StringResource(R.string.error_couldnt_load_profile)
                        )
                        )
                        return@launch
                    }
                    state = state.copy(profile.username)
                    state = state.copy(profile.bio)
                    profileState = profileState.copy(
                        profile = profile,
                        isLoading = false)
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.SnackbarEvent(
                        result.uiText ?: UiText.StringResource(R.string.error_unkown)
                    )
                    )
                    return@launch
                }
            }
        }
    }

    //Receives events from our screen
    fun onEvent(event: EditProfileEvent) {
        when(event) {
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
        val usernameResult = validateUsername(state.username)
       // val bioResult = validatePassword(state.bio)

        val hasError = listOf(
           // bioResult,
            usernameResult
        ).any { !it.successful}

        state = state.copy(
            //bioError = emailResult.errorMessage,
            usernameError = usernameResult.errorMessage
        )
        if(hasError)
            return


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






    sealed class UiEvent {
        data class SnackbarEvent(val uiText: UiText): UiEvent()
        data class Navigate(val route: String): UiEvent()
    }
}