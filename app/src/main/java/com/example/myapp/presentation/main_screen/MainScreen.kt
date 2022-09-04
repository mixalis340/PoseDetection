package com.example.myapp.presentation.main_screen

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

@Composable
fun MainScreen(
    navController: NavController
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
                        name = stringResource(id = R.string.squats_exercise),
                        imageUrl = painterResource(id = R.drawable.squats_exercise),
                        description = stringResource(id = R.string.squats_description)
                    ),
                 navController = navController
                ) }
            item {
                Exercise(
                    exercise = com.example.myapp.presentation.main_screen.models.Exercise(
                        name = stringResource(id = R.string.dumbbell),
                        imageUrl = painterResource(id = R.drawable.dumbbell_exercise),
                        description = stringResource(id = R.string.dumbbell_description)
                    ),
                  navController = navController
                )
            }
            item {
                Exercise(
                    exercise = com.example.myapp.presentation.main_screen.models.Exercise(
                        name = stringResource(id = R.string.shoulder_exercise),
                        imageUrl = painterResource(id = R.drawable.shoulder_exercise),
                        description = stringResource(id = R.string.shoulder_description)
                    ),
                    navController = navController
                )
            }
            item {
                Exercise(
                    exercise = com.example.myapp.presentation.main_screen.models.Exercise(
                        name = stringResource(id = R.string.arm_exercise),
                        imageUrl = painterResource(id = R.drawable.arm_lift_exercise),
                        description = stringResource(id = R.string.arm_description)
                    ),
                    navController = navController
                )
            }
            item {
                Exercise(
                    exercise = com.example.myapp.presentation.main_screen.models.Exercise(
                        name = stringResource(id = R.string.leg_exercise),
                        imageUrl = painterResource(id = R.drawable.leg_exercise),
                        description = stringResource(id = R.string.leg_description)
                    ),
                    navController = navController
                )
            }
            item {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }

    }


}