package com.example.myapp.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.example.myapp.R
import com.example.myapp.presentation.ui.theme.*

@Composable
fun ProfileHeaderSection(
    modifier: Modifier = Modifier,
    user:User,
    onEditClick: () -> Unit ={},
    onLogoutClick: () -> Unit ={}
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .offset(y = -ProfilePictureSizeLarge / 2f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(
                data = user.profilePictureUrl,
               imageLoader = ImageLoader.Builder(LocalContext.current)
                   .componentRegistry {
                       add(SvgDecoder(context))
                   }
                   .build()
            ) ,
            contentDescription ="",
            modifier = Modifier
                .size(ProfilePictureSizeLarge)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = CircleShape
                )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .offset(x = (30.dp + SpaceSmall) / 2f)

        ) {
            Text(
                text = user.username,
                style = MaterialTheme.typography.h1.copy(
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.width(SpaceSmall))
            IconButton(
                onClick = onEditClick,
                modifier = Modifier
                    .size(30.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_your_profile)
                )
            }
            Spacer(modifier = Modifier.width(SpaceSmall))
            IconButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .size(30.dp)
            ) {
                Icon(imageVector = Icons.Default.Logout,
                    contentDescription = stringResource(id = R.string.logout)
                )
            }
        }
        Spacer(modifier = Modifier.height(SpaceMedium))
        Text(
            text = user.description,
            style = MaterialTheme.typography.body2.copy(
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = SpaceLarge,
                    end = SpaceLarge,
                )
       )
    }
}