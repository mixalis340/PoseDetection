package com.example.myapp.data.response

import com.example.myapp.domain.repository.Profile

data class ProfileResponse(
    val userId: String,
    val username: String,
    val bio: String,
    val profilePictureUrl: String,
    val isOwnProfile: Boolean
) {
    fun  toProfile(): Profile {
        return Profile(
            userId = userId,
            username = username,
            bio = bio,
            profilePictureUrl = profilePictureUrl,
            isOwnProfile = isOwnProfile
        )
    }
}
