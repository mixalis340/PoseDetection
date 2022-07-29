package com.example.myapp.domain.use_case

import com.example.myapp.presentation.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
