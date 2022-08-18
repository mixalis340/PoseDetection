package com.example.myapp.domain.use_case

import com.example.myapp.SimpleResource
import com.example.myapp.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): SimpleResource {
        return repository.authenticate()
    }
}