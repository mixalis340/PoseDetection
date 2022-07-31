package com.example.myapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapp.presentation.components.BottomNavItem
import com.example.myapp.presentation.components.BottomNavigationBar
import com.example.myapp.presentation.login.LoginScreen
import com.example.myapp.presentation.ui.theme.MyAppTheme
import com.example.myapp.presentation.util.MainScreen
import com.example.myapp.presentation.util.Navigation
import com.example.myapp.presentation.util.Screen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    Scaffold(
                        bottomBar = {
                            if(navBackStackEntry?.destination?.route in listOf(
                                    Screen.MainScreen.route,
                                    Screen.ProfileScreen.route
                            )) {
                                BottomNavigationBar(
                                    items = listOf(
                                        BottomNavItem(
                                            name = "Home",
                                            route = Screen.MainScreen.route,
                                            icon = Icons.Outlined.Home
                                        ),
                                        BottomNavItem(
                                            name = "Profile",
                                            route = Screen.ProfileScreen.route,
                                            icon = Icons.Outlined.Person
                                        ),
                                    ),
                                    navController = navController,
                                    onItemClick = {
                                        navController.navigate(it.route)
                                    }
                                )
                            }
                        }

                    ) {
                        Navigation(navController)
                    }
                }
            }
        }
    }
}
@Composable
fun ChatScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chat screen")
    }
}

