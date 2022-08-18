package com.example.myapp.domain.use_case

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val updateProfile: UpdateProfileUseCase,
    val logout: LogoutUseCase
)

