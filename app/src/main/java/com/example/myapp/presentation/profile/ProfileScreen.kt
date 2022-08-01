package com.example.myapp.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.myapp.R
import com.example.myapp.presentation.profile.components.BannerSection
import com.example.myapp.presentation.profile.components.ProfileHeaderSection
import com.example.myapp.presentation.profile.components.User
import com.example.myapp.presentation.ui.theme.SpaceLarge
import com.example.myapp.presentation.ui.theme.SpaceMedium

@Composable
fun ProfileScreen(
    navController: NavController
) {
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
            ProfileHeaderSection(user = User(
                profilePictureUrl = "",
                username = "Michalis Ioannou",
                description = "MediaPipe Pose is a ML solution for high-fidelity body pose tracking, inferring 33 3D landmarks and background segmentation mask on the whole body from RGB ..."
                )
            )
        }
    }
}