package com.example.myapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapp.R
import com.example.myapp.presentation.main_screen.models.Exercise
import com.example.myapp.presentation.ui.theme.MediumGray
import com.example.myapp.presentation.ui.theme.SpaceMedium
import com.example.myapp.presentation.util.Screen

@Composable
fun Exercise(
    exercise: Exercise,
    onExerciseClick: () -> Unit = {},
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MediumGray)
        ) {
            Image(
                painter = exercise.imageUrl,
                contentDescription = "Exercise image",
                modifier = Modifier.clickable {
                    navController.navigate(route = Screen.CameraScreen.route + "/${exercise.name}")
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceMedium)
            ) {
                //Spacer(modifier = Modifier.height(SpaceMedium))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = exercise.name,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary
                        )
                    )
                }
                Spacer(modifier = Modifier.height(SpaceMedium))
                Text(
                    text = exercise.description,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}