package com.example.myapp.presentation.login


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.example.myapp.Resource
import com.example.myapp.SimpleResource
import com.example.myapp.presentation.components.StandardTextField
import com.example.myapp.presentation.profile.ProfileScreen
import com.example.myapp.presentation.register.RegisterViewModel
import com.example.myapp.presentation.ui.theme.SpaceLarge
import com.example.myapp.presentation.ui.theme.SpaceMedium
import com.example.myapp.presentation.util.Screen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: LoginViewModel= hiltViewModel()
){
        val state = viewModel.state
        val context = LocalContext.current

    LaunchedEffect(key1 = true)   {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is LoginViewModel.UiEvent.SnackbarEvent-> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
                }
                is LoginViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    LaunchedEffect(key1 = true)   {
        viewModel.authResults.collect { result ->
            when(result) {
                is Resource.Success ->{
                    navController.popBackStack()
                    navController.navigate(Screen.MainScreen.route)
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
            StandardTextField(
                text = state.email,
                hint = stringResource(id = R.string.email_hint),
                onValueChange = {
                                viewModel.onEvent(LoginEvent.EmailChanged(it))
                },
                keyboardType = KeyboardType.Email,
                error = state.emailError?.asString()
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = state.password,
                hint = stringResource(id = R.string.password_hint),
                onValueChange = {
                    viewModel.onEvent(LoginEvent.PasswordChanged(it))
                },
                keyboardType = KeyboardType.Password,
                error = state.passwordError?.asString()
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(onClick = {
                viewModel.onEvent(LoginEvent.Submit)
            },
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colors.onPrimary
                )
            }
            if(state.isLoading) {
                CircularProgressIndicator(modifier = Modifier
                    .align(CenterHorizontally)
                )
            }
        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dont_have_an_account_yet))
                append(" ")
                withStyle(style = SpanStyle(
                    color = MaterialTheme.colors.primary
                    )
                ){
                    append(stringResource(id = R.string.sign_up))
                }
            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    navController.navigate(
                        Screen.RegisterScreen.route
                    )
                }
        )

        }
    }

