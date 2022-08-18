package com.example.myapp.presentation.edit_profile

import android.net.Uri


sealed class EditProfileEvent {
    data class UsernameChanged(val username: String): EditProfileEvent()
    data class BioChanged(val bio: String): EditProfileEvent()
    data class CropProfilePicture(val uri:Uri?): EditProfileEvent()

    object Submit: EditProfileEvent()
}
