package com.example.myapp.util

sealed class Screen(val route: String){
    object LoginScreen: Screen("login_screen")
    object Mainscreen: Screen("main_screen")

    fun wirthArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
