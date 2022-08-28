package com.example.myapp.presentation.profile

import com.example.myapp.domain.repository.Profile

sealed class ProfileEvent{
    object DismissLogoutDialog: ProfileEvent()
    object ShowLogoutDialog: ProfileEvent()
    object Logout: ProfileEvent()
}
