package com.example.myapp.data.remote

import com.example.myapp.data.BasicApiResponse
import com.example.myapp.data.response.ProfileResponse
import com.example.myapp.presentation.edit_profile.EditProfileEvent
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileApi {

    @GET("/api/user/profile")
    suspend fun getProfile(
       @Query("userId") userId: String
    ): BasicApiResponse<ProfileResponse>

    @Multipart
    @PUT("/api/user/update")
    suspend fun updateProfile(
        @Part profilePicture: MultipartBody.Part?,
        @Part updateProfileData: MultipartBody.Part
    ): BasicApiResponse<Unit>

    companion object {
            const val BASE_URL = "http://192.168.1.6:8080/"
    }

}