package com.example.myapp.presentation.edit_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapp.R
import com.example.myapp.presentation.components.StandardTextField
import com.example.myapp.presentation.components.StandardToolbar
import com.example.myapp.presentation.login.LoginEvent
import com.example.myapp.presentation.login.LoginViewModel
import com.example.myapp.presentation.profile.components.BannerSection
import com.example.myapp.presentation.ui.theme.ProfilePictureSizeLarge
import com.example.myapp.presentation.ui.theme.SpaceLarge
import com.example.myapp.presentation.ui.theme.SpaceMedium

@Composable
fun EditProfileScreen(
    navController: NavController,
    profilePictureSize: Dp = ProfilePictureSizeLarge
) {
    val viewModel = viewModel<EditProfileViewModel>()
    val state = viewModel.state
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.15f).dp
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        StandardToolbar(
            showBackArrow = true,
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.edit_your_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(bannerHeight + profilePictureSize / 2f)
            ) {
                BannerSection( modifier = Modifier
                    .aspectRatio(2.15f)
                )
                Image(
                    painter = painterResource(id = R.drawable.mike_image),
                    contentDescription =null,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(profilePictureSize)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.onSurface,
                            shape = CircleShape
                        )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceLarge)
            ) {
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    text = state.username,
                    hint = stringResource(id = R.string.username_hint),
                    onValueChange = {
                        viewModel.onEvent(EditProfileEvent.UsernameChanged(it))
                    },
                    error = state.usernameError?.asString()
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    text = state.email,
                    hint = stringResource(id = R.string.email_hint),
                    onValueChange = {
                        viewModel.onEvent(EditProfileEvent.EmailChanged(it))
                    },
                    error = state.emailError?.asString()
                )
            }
        }
    }
}