package com.example.myapp.domain.use_case

import android.util.Patterns
import com.example.myapp.Constants
import com.example.myapp.R
import com.example.myapp.presentation.UiText

object Validation {

    fun validateEmail(email: String): ValidationResult {
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

    fun validatePassword(password: String): ValidationResult {
        if(password.isBlank()) {
            return  ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.this_field_cant_be_empty)
            )
        }
        if(password.length < Constants.MIN_PASSWORD_LENGTH) {
            return  ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    resId = R.string.input_too_short,
                    Constants.MIN_PASSWORD_LENGTH
                )
            )
        }
        val digitsInPassword = password.any { it.isDigit() }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        if(!digitsInPassword || !capitalLettersInPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_password)
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    fun validateUsername(username: String): ValidationResult {
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