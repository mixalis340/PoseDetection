package com.example.myapp.util

sealed class Screen(val route: String){
    object LoginScreen: Screen("login_screen")
}
