package com.example.myapp.presentation.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapp.presentation.edit_profile.EditProfileScreen
import com.example.myapp.presentation.login.LoginScreen
import com.example.myapp.presentation.main_screen.Camera
import com.example.myapp.presentation.profile.ProfileScreen
import com.example.myapp.presentation.register.RegisterScreen
import com.example.myapp.presentation.settings.SettingsScreen


@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState
){
    NavHost(navController = navController,
            startDestination = Screen.LoginScreen.route,
            modifier = Modifier.fillMaxSize()
        ){

        composable(route = Screen.LoginScreen.route){
            LoginScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(route = Screen.RegisterScreen.route){
            RegisterScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(route = Screen.MainScreen.route){
            com.example.myapp.presentation.main_screen.MainScreen(navController = navController)
        }
        composable(route = Screen.CameraScreen.route) {
            Camera(navController = navController)
        }
        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
        composable(
            route = Screen.ProfileScreen.route,
        ){

            ProfileScreen(
                userId = it.arguments?.getString("userId"),
                onLogout = {
                         navController.navigate(
                             route = Screen.LoginScreen.route
                         ) {
                             popUpTo(Screen.LoginScreen.route) {
                                 inclusive = true
                             }
                         }
                },
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(route = Screen.EditProfileScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(name="userId") {
                    type = NavType.StringType
                }
            )
            ) {
            EditProfileScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }

    }
}
