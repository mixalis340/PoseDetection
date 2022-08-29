package com.example.myapp.presentation.edit_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.example.myapp.R
import com.example.myapp.presentation.components.StandardTextField
import com.example.myapp.presentation.components.StandardToolbar
import com.example.myapp.presentation.login.LoginEvent
import com.example.myapp.presentation.login.LoginViewModel
import com.example.myapp.presentation.profile.ProfileState
import com.example.myapp.presentation.profile.components.BannerSection
import com.example.myapp.presentation.ui.theme.ProfilePictureSizeLarge
import com.example.myapp.presentation.ui.theme.SpaceLarge
import com.example.myapp.presentation.ui.theme.SpaceMedium
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditProfileScreen(
    scaffoldState: ScaffoldState,
    navController: NavController,
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    viewModel: EditProfileViewModel= hiltViewModel()
) {

    val state = viewModel.state
    val profileState = viewModel.profileState
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.15f).dp

    val cropProfilePictureLauncherLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(EditProfileEvent.CropProfilePicture(it))
    }

    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        cropProfilePictureLauncherLauncher.launch(it)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is EditProfileViewModel.UiEvent.SnackbarEvent -> {
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
    ){
        StandardToolbar(
            showBackArrow = true,
            navController = navController,
            navActions = {
                IconButton(onClick = {
                    viewModel.onEvent(EditProfileEvent.Submit)
                }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.save_changes),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            },
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
            BannerEditSection(
                profileImage = rememberImagePainter(
                    data = state.profilePictureUri ?: profileState.profile?.profilePictureUrl,
                    imageLoader = ImageLoader.Builder(LocalContext.current)
                        .componentRegistry {
                            add(SvgDecoder(context))
                        }
                        .build()
                ),
                profilePictureSize = profilePictureSize,
                onProfilePictureClick = {
                    profilePictureGalleryLauncher.launch("image/*")
                }
            )
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
                    error = state.usernameError?.asString(),
                    leadingIcon = Icons.Default.Person
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    text = state.bio,
                    hint = stringResource(id = R.string.bio_hint),
                    onValueChange = {
                        viewModel.onEvent(EditProfileEvent.BioChanged(it))
                    },
                    leadingIcon =Icons.Default.Description,
                    singleLine = false
                )
                if(state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier
                        .padding(top = SpaceLarge)
                        .align(Alignment.CenterHorizontally)
                    )
                }

            }
        }
    }

}
@Composable
fun BannerEditSection(
    profileImage: Painter,
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    onProfilePictureClick: () -> Unit = {}
) {
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.15f).dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight + profilePictureSize / 2f)
    ) {
        BannerSection(
            modifier = Modifier
                .aspectRatio(2.15f)
        )
        Image(
            painter = profileImage,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(profilePictureSize)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = CircleShape
                )
                .clickable {
                    onProfilePictureClick()
                }
        )
    }
}
