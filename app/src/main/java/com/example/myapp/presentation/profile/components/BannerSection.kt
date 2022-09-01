package com.example.myapp.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.myapp.R

@Composable
fun BannerSection(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    )
    {
        Image(
            painter = painterResource(id = R.drawable.pose_banner),
            contentDescription = stringResource(id = R.string.banner_image),
            modifier = Modifier.fillMaxSize(),
            )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 150f
                    )
                )
        )
    }


}


