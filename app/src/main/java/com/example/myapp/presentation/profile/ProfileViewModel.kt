package com.example.myapp.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.R
import com.example.myapp.Resource
import com.example.myapp.domain.use_case.GetOwnUserIdUseCase
import com.example.myapp.domain.use_case.ProfileUseCases
import com.example.myapp.presentation.UiText
import com.example.myapp.presentation.register.RegisterState
import com.example.myapp.presentation.register.RegisterViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle,
    private val getOwnUserId: GetOwnUserIdUseCase,
) : ViewModel(){

    var state by mutableStateOf(ProfileState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.Logout -> {
                profileUseCases.logout()
            }
            is ProfileEvent.ShowLogoutDialog -> {
                state = state.copy(isLogoutDialogVisible = true)
            }
            is ProfileEvent.DismissLogoutDialog -> {
                state = state.copy(isLogoutDialogVisible = false)
            }
        }
    }

    fun getProfile(userId: String?) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
          val result = profileUseCases.getProfile(userId ?: getOwnUserId())
            when(result) {
                is Resource.Success -> {
                    state = state.copy(
                        profile = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(uiText = result.uiText ?: UiText.StringResource(R.string.error_unkown))
                    )
                }
            }
        }
    }

    sealed class UiEvent {
        data class SnackbarEvent(val uiText: UiText): UiEvent()
    }

}