package com.example.myapp.domain.use_case

import android.util.Patterns
import com.example.myapp.Constants
import com.example.myapp.R
import com.example.myapp.presentation.UiText
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


class ValidateUsername {

    fun execute(username: String): ValidationResult {
        val trimmedUsername = username.trim()
        if(trimmedUsername.isBlank()) {
            return  ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.this_field_cant_be_empty)
            )
        }
        if(trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.input_too_short,
                    Constants.MIN_USERNAME_LENGTH
                )
            )
        }
        return ValidationResult(
            successful = true,
        )
    }
}

