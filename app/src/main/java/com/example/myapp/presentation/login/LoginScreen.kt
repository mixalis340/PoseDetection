package com.example.myapp.presentation.login


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.R
import com.example.myapp.presentation.components.StandardTextField
import com.example.myapp.presentation.ui.theme.SpaceLarge
import com.example.myapp.presentation.ui.theme.SpaceMedium
import com.example.myapp.presentation.ui.theme.SpaceSmall
import kotlin.math.sinh


@Composable
fun LoginScreen(){

        val viewModel = viewModel<LoginViewModel>()
        val state = viewModel.state
        val context = LocalContext.current
        LaunchedEffect(key1 = context)   {
            viewModel.validationEvents.collect { event ->
                when(event) {
                    is LoginViewModel.ValidationEvent.Success -> {
                        Toast.makeText(
                            context,
                            "IT is ok",
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
    )
    {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            var textFieldState1 by remember {
                mutableStateOf("")
            }
            var textFieldState2 by remember {
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
                hint = "Password",
                onValueChange = {
                    textFieldState2 = it
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
                    text = "Login",
                    color = MaterialTheme.colors.onPrimary
                )
            }



            /* ERROS HANDLING!!
            TextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                },
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Email")
                }
                )

                if (state.emailError != null) {
                    Text(
                        text = state.emailError,
                        color = MaterialTheme.colors.error,
                        modifier= Modifier.align(Alignment.End)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.onEvent(LoginFormEvent.Submit)
            },
                modifier= Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Submit",
                    color = MaterialTheme.colors.background
                )
            }*/
        }
        Text(
            text = buildAnnotatedString {
                append("Don't have an account yet?")
                append(" ")
                withStyle(style = SpanStyle(
                    color = MaterialTheme.colors.primary
                    )
                ){
                    append("Sign up!")
                }
            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )

        }
    }

