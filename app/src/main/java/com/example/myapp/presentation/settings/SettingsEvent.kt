package com.example.myapp.presentation.settings

sealed class SettingsEvent {
    object Checked: SettingsEvent()
    object UnChecked: SettingsEvent()
}
