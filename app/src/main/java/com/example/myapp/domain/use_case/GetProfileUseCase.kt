package com.example.myapp.domain.use_case

import com.example.myapp.Resource
import com.example.myapp.domain.repository.Profile
import com.example.myapp.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String) : Resource<Profile> {
        return repository.getProfile(userId)
    }
}