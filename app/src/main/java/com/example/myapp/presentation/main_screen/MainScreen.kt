package com.example.myapp.presentation.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapp.R
import com.example.myapp.presentation.components.StandardToolbar

@Composable
fun MainScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
       StandardToolbar(
           navController = navController,
           title = {
               Text(
                   text = stringResource(id = R.string.home),
                   fontWeight = FontWeight.Bold,
                   color = MaterialTheme.colors.onBackground,
               )
           },
           modifier = Modifier.fillMaxWidth(),
       )
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("MainScreen")
    }
}