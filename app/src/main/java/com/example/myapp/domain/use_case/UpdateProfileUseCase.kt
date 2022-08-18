package com.example.myapp.domain.use_case

import android.net.Uri
import com.example.myapp.R
import com.example.myapp.Resource
import com.example.myapp.SimpleResource
import com.example.myapp.data.request.UpdateProfileData
import com.example.myapp.domain.repository.ProfileRepository
import com.example.myapp.presentation.UiText

class UpdateProfileUseCase (
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(updateProfileData: UpdateProfileData, profilePictureUri: Uri?): SimpleResource{
        if(updateProfileData.username.isBlank()) {
            return Resource.Error (
                uiText = UiText.StringResource(R.string.error_username_empty)
                    )
        }
        return repository.updateProfile(
            updateProfileData = updateProfileData,
            profilePictureUri = profilePictureUri
        )
    }

}