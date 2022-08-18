package com.example.myapp.domain.repository

data class Profile(
    val userId: String,
    val username: String,
    val bio: String,
    val profilePictureUrl: String,
    val isOwnProfile: Boolean
)
