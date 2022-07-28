package com.example.myapp.presentation.register

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapp.presentation.components.StandardTextField
import com.example.myapp.presentation.login.LoginEvent
import com.example.myapp.presentation.login.LoginViewModel
import com.example.myapp.presentation.ui.theme.SpaceLarge
import com.example.myapp.presentation.ui.theme.SpaceMedium
import com.example.myapp.presentation.util.Screen

@Composable
fun RegisterScreen(navController: NavController) {

    val viewModel = viewModel<RegisterViewModel>()
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context)   {
        viewModel.validationEvents.collect { event ->
            when(event) {
                is RegisterViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Register successful",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
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


            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = state.email,
                hint = "E-mail",
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EmailChanged(it))
                },
                singleLine = true,
                error = state.emailError.orEmpty()
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = state.username,
                hint = "Username",
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.UsernameChanged(it))
                },
                singleLine = true,
                error = state.usernameError.orEmpty()
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = state.password,
                hint = "Password",
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.PasswordChanged(it))
                },
                singleLine = true,
                keyboardType = KeyboardType.Password,
                error = state.passwordError.orEmpty()
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(onClick = {
                viewModel.onEvent(RegisterEvent.Submit)
            },
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