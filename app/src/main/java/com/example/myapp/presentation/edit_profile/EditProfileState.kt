package com.example.myapp.presentation.edit_profile

import android.net.Uri
import com.example.myapp.presentation.UiText

data class EditProfileState(
    //When user opens the screen, this is the state that reflects what he should see first
    val username: String = "",
    val usernameError: UiText? = null,
    val bio: String = "",
    val bioError: UiText? = null,
    val profilePictureUri: Uri? = null,
    val isLoading: Boolean = false
)

