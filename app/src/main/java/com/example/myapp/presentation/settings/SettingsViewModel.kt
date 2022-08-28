package com.example.myapp.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapp.Constants
import javax.inject.Inject

class SettingsViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle)
    :ViewModel(){

    private val KEY = Constants.KEY_FRAME_LIKELIHOOD
     var state by mutableStateOf(SettingsState())


    fun onEvent(event:SettingsEvent) {
     when(event) {
         is SettingsEvent.Checked ->{
             state = state.copy(isChecked = true)
         }
         is SettingsEvent.UnChecked -> {
             state = state.copy(isChecked = false)
         }
     }
    }


}