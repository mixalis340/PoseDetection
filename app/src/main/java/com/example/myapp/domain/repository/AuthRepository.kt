package com.example.myapp.domain.repository

import com.example.myapp.Resource
import com.example.myapp.SimpleResource
import com.example.myapp.data.BasicApiResponse
import com.example.myapp.data.request.CreateAccountRequest

interface AuthRepository {
    suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource


    suspend fun  login(
        email: String,
        password: String
    ): SimpleResource


    suspend fun authenticate(): SimpleResource


}