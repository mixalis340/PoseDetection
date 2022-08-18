package com.example.myapp.domain.use_case

import com.example.myapp.domain.repository.ProfileRepository

class LogoutUseCase(
    private val repository: ProfileRepository
) {
    operator fun invoke() {
        repository.logout()
    }
}