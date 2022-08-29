package com.example.myapp.data.remote

import com.example.myapp.data.BasicApiResponse
import com.example.myapp.data.request.CreateAccountRequest
import com.example.myapp.data.request.LoginRequest
import com.example.myapp.data.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): BasicApiResponse<Unit>

    @POST("/api/user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): BasicApiResponse<AuthResponse>

    @GET("/api/user/authenticate")
    suspend fun authenticate()

    companion object {
        const val BASE_URL = "http://192.168.1.6:8080/"
    }
}