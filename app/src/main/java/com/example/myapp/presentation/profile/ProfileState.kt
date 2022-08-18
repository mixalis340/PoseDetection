package com.example.myapp.presentation.profile

import com.example.myapp.domain.repository.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val isLogoutDialogVisible: Boolean = false
)
