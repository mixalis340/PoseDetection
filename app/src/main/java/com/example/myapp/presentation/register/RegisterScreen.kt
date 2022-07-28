package com.example.myapp.presentation.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapp.R
import com.example.myapp.presentation.components.StandardTextField
import com.example.myapp.presentation.ui.theme.SpaceLarge
import com.example.myapp.presentation.ui.theme.SpaceMedium
import com.example.myapp.util.Screen

@Composable
fun RegisterScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SpaceLarge,
                end = SpaceLarge,
                top = SpaceLarge,
                bottom = 50.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            var textFieldState1 by remember {
                mutableStateOf("")
            }
            var textFieldState2 by remember {
                mutableStateOf("")
            }
            var textFieldState3 by remember {
                mutableStateOf("")
            }
            StandardTextField(
                text = textFieldState1,
                hint = "E-mail",
                onValueChange = {
                    textFieldState1 = it
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = textFieldState2,
                hint = "Username",
                onValueChange = {
                    textFieldState2 = it
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = textFieldState3,
                hint = "Password",
                onValueChange = {
                    textFieldState3 = it
                },
                singleLine = true,
                keyboardType = KeyboardType.Password
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(onClick = {},
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text(
                    text = "Register",
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        Text(
            text = buildAnnotatedString {
                append("Already have an account?")
                append(" ")
                withStyle(style = SpanStyle(
                    color = MaterialTheme.colors.primary
                )
                ){
                    append("Sign in!")
                }
            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    navController.navigate(
                        Screen.LoginScreen.route
                    )
                }
        )
    }

}