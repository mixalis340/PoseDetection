package com.example.myapp.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            painter = painterResource(id = R.drawable.banner_image),
            contentDescription = stringResource(id = R.string.banner_image),
            modifier = Modifier.fillMaxSize(),
            )
    }

}


