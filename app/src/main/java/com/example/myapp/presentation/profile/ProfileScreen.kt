package com.example.myapp.presentation.profile

import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.End
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.myapp.R
import com.example.myapp.presentation.components.StandardToolbar
import com.example.myapp.presentation.profile.components.BannerSection
import com.example.myapp.presentation.profile.components.ProfileHeaderSection
import com.example.myapp.presentation.profile.components.User
import com.example.myapp.presentation.ui.theme.SpaceLarge
import com.example.myapp.presentation.ui.theme.SpaceMedium
import com.example.myapp.presentation.ui.theme.SpaceSmall
import com.example.myapp.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoilApi
@Composable
fun ProfileScreen(
    userId: String? = null,
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    onLogout: () -> Unit = {}
) {
    val state = viewModel.state
    val context = LocalContext.current
    
    LaunchedEffect(key1 = true) {
        viewModel.getProfile(userId)
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is ProfileViewModel.UiEvent.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.your_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                BannerSection(
                    modifier = Modifier
                        .aspectRatio(2.15f)
                )
            }
            item {
                state.profile?.let { profile ->
                    ProfileHeaderSection(
                        user = User(
                        userId = profile.userId,
                        profilePictureUrl = profile.profilePictureUrl,
                        username = profile.username,
                        description = profile.bio
                    ),
                        onEditClick = {
                            navController.navigate(Screen.EditProfileScreen.route + "/${profile.userId}")
                        },
                        onLogoutClick = {
                            viewModel.onEvent(ProfileEvent.ShowLogoutDialog)
                        }
                    )
                }

            }
        }
        if(state.isLogoutDialogVisible) {
            Dialog(onDismissRequest = {
                viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
            }){
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(SpaceMedium)
                ) {
                    Text(text = stringResource(id = R.string.do_you_want_to_logout))
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text =  stringResource(id = R.string.no).uppercase(),
                            color = MaterialTheme.colors.onBackground,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                            }
                        )
                        Spacer(modifier = Modifier.width(SpaceMedium))
                        Text(
                            text =  stringResource(id = R.string.yes).uppercase(),
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(ProfileEvent.Logout)
                                viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                                onLogout()
                            }
                        )
                    }
                }
            }
        }
    }
}