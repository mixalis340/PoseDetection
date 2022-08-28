package com.example.myapp.domain.use_case

import com.example.myapp.SimpleResource
import com.example.myapp.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {


    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        return repository.register(email.trim(), username.trim(), password.trim())
    }
}