package com.example.myapp.domain.use_case

import android.util.Patterns
import androidx.compose.ui.platform.LocalContext
import com.example.myapp.R
import com.example.myapp.presentation.UiText

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
                errorMessage = UiText.StringResource(R.string.indalid_email)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}