package com.example.myapp.domain.repository

import android.content.SharedPreferences
import com.example.myapp.Constants
import com.example.myapp.R
import com.example.myapp.Resource
import com.example.myapp.SimpleResource
import com.example.myapp.data.remote.AuthApi
import com.example.myapp.data.request.CreateAccountRequest
import com.example.myapp.data.request.LoginRequest
import com.example.myapp.presentation.UiText
import com.example.myapp.presentation.login.LoginScreen
import retrofit2.HttpException
import java.io.IOException

//The repository decides from where the app gets the data from. I POST email,username,password to the API
//and get either if that was successful or not
class AuthRepositoryImpl (
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
): AuthRepository {
    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        val request = CreateAccountRequest(email, username, password)
        return try {
            val response = api.register(request)
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

    override suspend fun login(email: String, password: String): SimpleResource {
        val request = LoginRequest(email, password)
        return try {
            val response = api.login(request)
            if (response.successful) {
                response.data?.let { authResponse ->
                    sharedPreferences.edit()
                        .putString(Constants.KEY_JWT_TOKEN, authResponse.token)
                        .putString(Constants.KEY_USER_ID, authResponse.userId)
                        .apply()
                }
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

    override suspend fun authenticate(): SimpleResource{
        return try {
            api.authenticate()
            Resource.Success(Unit)
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
}


