package com.example.myapp.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapp.presentation.ui.theme.MediumGray
import com.example.myapp.R

@Composable
fun StandardToolbar(
    navController: NavController,
    modifier: Modifier = Modifier,
    title : @Composable () -> Unit = {},
    showBackArrow: Boolean = false,
    navActions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = if(showBackArrow) {
            {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back) ,
                        tint = MaterialTheme.colors.onBackground
                    )
                }
             }
            } else null,
        actions = navActions,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    )

}