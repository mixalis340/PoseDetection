package com.example.myapp.domain.use_case

import android.util.Patterns
import androidx.compose.ui.platform.LocalContext
import com.example.myapp.R
import com.example.myapp.presentation.UiText
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


class ValidateEmail {

    fun execute(email: String): ValidationResult {
        val trimmedEmail = email.trim()
        if(trimmedEmail.isBlank()) {
            return  ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.this_field_cant_be_empty)
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_email)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}