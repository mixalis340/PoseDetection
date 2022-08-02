package com.example.myapp.presentation.edit_profile


sealed class EditProfileEvent {
    data class EmailChanged(val email: String): EditProfileEvent()
    data class UsernameChanged(val username: String): EditProfileEvent()
    data class BioChanged(val bio: String): EditProfileEvent()

    object Submit: EditProfileEvent()
}
