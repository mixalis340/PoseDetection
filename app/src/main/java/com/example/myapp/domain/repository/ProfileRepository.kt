package com.example.myapp.domain.repository

import android.net.Uri
import com.example.myapp.Resource
import com.example.myapp.SimpleResource
import com.example.myapp.data.request.UpdateProfileData

interface ProfileRepository {

    suspend fun getProfile(userId: String): Resource<Profile>

    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?
    ): SimpleResource

    fun logout()
}