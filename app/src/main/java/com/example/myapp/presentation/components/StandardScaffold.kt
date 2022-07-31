package com.example.myapp.presentation.components

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myapp.presentation.util.Navigation
import com.example.myapp.presentation.util.Screen

@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    bottomNavItems: List<BottomNavItem>
) {
   Scaffold (
       bottomBar = {
           BottomNavigationBar(
               items = listOf(
                   BottomNavItem(
                       name = "Home",
                       route = "home",
                       icon = Icons.Default.Home
                   ),
                   BottomNavItem(
                       name = "Chat",
                       route = "chat",
                       icon = Icons.Default.Notifications
                   ),
                   BottomNavItem(
                       name = "Settings",
                       route = "settings",
                       icon = Icons.Default.Settings
                   ),
               ),
               navController = navController,
               onItemClick = {
                   navController.navigate(it.route)
               }
           )
       }
           ) {

    }
}