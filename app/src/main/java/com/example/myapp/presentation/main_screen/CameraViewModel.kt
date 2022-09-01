package com.example.myapp.presentation.main_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
):ViewModel() {
  private val name: String? = savedStateHandle["name"]

   fun getName(): String? {
        return name
    }
 }
