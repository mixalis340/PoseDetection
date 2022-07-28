package com.example.myapp.domain.use_case

import android.util.Patterns
import com.example.myapp.Constants

class ValidateUsername {

    fun execute(username: String): ValidationResult {
        val trimmedUsername = username.trim()
        if(trimmedUsername.isBlank()) {
            return  ValidationResult(
                successful = false,
                errorMessage = "This field can't be empty"
            )
        }
        if(trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = "The minimum length is ${Constants.MIN_USERNAME_LENGTH}"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}