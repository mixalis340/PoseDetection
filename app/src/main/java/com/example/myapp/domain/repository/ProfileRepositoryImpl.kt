package com.example.myapp.domain.repository

import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toFile
import com.example.myapp.Constants
import com.example.myapp.R
import com.example.myapp.Resource
import com.example.myapp.SimpleResource
import com.example.myapp.data.remote.ProfileApi
import com.example.myapp.data.request.CreateAccountRequest
import com.example.myapp.data.request.UpdateProfileData
import com.example.myapp.presentation.UiText
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl (
    private val api: ProfileApi,
    private val gson:Gson,
    private val sharedPreferences: SharedPreferences
        ) : ProfileRepository {

    override suspend fun getProfile(userId: String): Resource<Profile> {
        return try {
            val response = api.getProfile(userId)
            if (response.successful) {
                Resource.Success(response.data?.toProfile())
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unkown))

            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_something_went_wrong)
            )
        }
    }

    override suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?
    ): SimpleResource {
        val profilePictureFile = profilePictureUri?.toFile()

        return try {
            val response = api.updateProfile(
                profilePicture = profilePictureFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "profile_picture",
                            profilePictureFile.name,
                            profilePictureFile.asRequestBody()
                        )
                },
                updateProfileData = MultipartBody.Part
                    .createFormData(
                        "update_profile_data",
                        gson.toJson(updateProfileData)
                    )
            )
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unkown))

            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_something_went_wrong)
            )
        }
    }

    override fun logout() {
        sharedPreferences.edit()
            .remove(Constants.KEY_JWT_TOKEN)
            .apply()
    }
}