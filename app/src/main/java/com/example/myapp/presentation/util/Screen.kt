package com.example.myapp.presentation.util

sealed class Screen(val route: String){
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object MainScreen: Screen("main_screen")
    object CameraScreen: Screen("camera_screen")
    object ProfileScreen: Screen("profile_screen")
    object EditProfileScreen: Screen("edit_profile_screen")
    object SettingsScreen: Screen("settings_screen")

}
