package com.example.myapp.domain.use_case

import android.util.Patterns

class ValidateUsername {

    fun execute(username: String): ValidationResult {
        val trimmedUsername = username.trim()
        if(trimmedUsername.isBlank()) {
            return  ValidationResult(
                successful = false,
                errorMessage = "This field can't be empty"
            )
        }
        if(trimmedUsername.length < 3) {
            return ValidationResult(
                successful = false,
                errorMessage = "The minimum length is 3"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}