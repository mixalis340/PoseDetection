package com.example.myapp.domain.use_case

import android.util.Patterns
import com.example.myapp.Constants

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if(password.isBlank()) {
            return  ValidationResult(
                successful = false,
                errorMessage = "This field can't be empty"
            )
        }
        if(password.length < Constants.MIN_PASSWORD_LENGTH) {
            return  ValidationResult(
                successful = false,
                errorMessage = "The minimum length is ${Constants.MIN_PASSWORD_LENGTH}"
            )
        }
        val digitsInPassword = password.any { it.isDigit() }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        if(!digitsInPassword || !capitalLettersInPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one uppercase letter and one digit"
            )
        }
        return ValidationResult(
            successful = true
        )
    }


}