package com.example.myapp.domain.use_case

import android.util.Patterns
import com.example.myapp.Constants
import com.example.myapp.R
import com.example.myapp.presentation.UiText

class ValidatePassword {

    fun execute(password: String): ValidationResult {
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
                    resId = R.string.min_name_length_error,
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


}