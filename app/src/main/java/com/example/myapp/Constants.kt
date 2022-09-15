package com.example.myapp

import com.example.myapp.presentation.UiText

object Constants {
    const val MIN_USERNAME_LENGTH = 3
    const val MIN_PASSWORD_LENGTH = 4

    const val KEY_JWT_TOKEN = "jwt_token"

    const val SHARED_PREF_NAME = "shared_pref"

    const val KEY_USER_ID = "userId"



    const val DOT_RADIUS = 3.0f

    const val STROKE_WIDTH = 3.5f

    var squatsCounter = 0
    var dumbbellCounter = 0
    var shoulderCounter = 0
    var armCounter = 0
    var legCounter = 0

    var text : UiText? = null
    var stage = "none"
    var isCount = false
}