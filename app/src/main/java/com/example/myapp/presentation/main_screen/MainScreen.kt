package com.example.myapp.presentation.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapp.R
import com.example.myapp.presentation.components.Exercise
import com.example.myapp.presentation.components.StandardToolbar
import com.example.myapp.presentation.util.Screen

@Composable
fun MainScreen(
    navController: NavController,
    onExerciseClick: () -> Unit= {}
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.your_exercises),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Exercise(
                    exercise = com.example.myapp.presentation.main_screen.models.Exercise(
                        name = "Push-Ups",
                        imageUrl = painterResource(id = R.drawable.squats_exercise),
                        description = "Start your push-ups exercise like the picture above!"
                    ),
                    modifier = Modifier.clickable {
                        onExerciseClick()
                    }
                ) }
            item {
                Exercise(
                    exercise = com.example.myapp.presentation.main_screen.models.Exercise(
                        name = "Push-Ups",
                        imageUrl = painterResource(id = R.drawable.push_ups_exercise),
                        description = "Start your push-ups exercise like the picture above!"
                    ),
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.CameraScreen.route)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }

    }


}