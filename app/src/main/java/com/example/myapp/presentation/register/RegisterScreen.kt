package com.example.myapp.presentation.register

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapp.R
import com.example.myapp.presentation.components.StandardTextField
import com.example.myapp.presentation.login.LoginEvent
import com.example.myapp.presentation.login.LoginViewModel
import com.example.myapp.presentation.ui.theme.SpaceLarge
import com.example.myapp.presentation.ui.theme.SpaceMedium
import com.example.myapp.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: RegisterViewModel = hiltViewModel()
) {


    val state = viewModel.state
    val context = LocalContext.current


    LaunchedEffect(key1 = true)   {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is RegisterViewModel.UiEvent.SnackbarEvent-> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
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
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = state.email,
                hint = stringResource(id = R.string.email_hint),
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EmailChanged(it))
                },
                keyboardType = KeyboardType.Email,
                error = state.emailError?.asString()
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = state.username,
                hint = stringResource(id = R.string.username_hint),
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.UsernameChanged(it))
                },
                error = state.usernameError?.asString()
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = state.password,
                hint = stringResource(id = R.string.password_hint),
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.PasswordChanged(it))
                },
                keyboardType = KeyboardType.Password,
                error = state.passwordError?.asString()
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(onClick = {
                viewModel.onEvent(RegisterEvent.Submit)
            },
                enabled = !state.isLoading,
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    color = MaterialTheme.colors.onPrimary
                )
            }
            if(state.isLoading)
                CircularProgressIndicator()

        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.already_have_an_account))
                append(" ")
                withStyle(style = SpanStyle(
                    color = MaterialTheme.colors.primary
                )
                ){
                    append(stringResource(id = R.string.sign_in))
                }
            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    navController.popBackStack()
                }
        )
    }

}