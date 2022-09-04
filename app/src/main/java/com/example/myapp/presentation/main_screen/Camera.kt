package com.example.myapp.presentation.main_screen

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.myapp.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Camera(
    navController: NavController,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    val lifecycleOwner = LocalLifecycleOwner.current
   DisposableEffect(
       key1 = lifecycleOwner,
       effect = {
           val observer = LifecycleEventObserver { _, event ->
                if(event == Lifecycle.Event.ON_START) {
                    permissionState.launchPermissionRequest()
                }
           }
           lifecycleOwner.lifecycle.addObserver(observer)

           onDispose {
               lifecycleOwner.lifecycle.removeObserver(observer)
           }
       }
   )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            permissionState.hasPermission -> {
                CameraView(navController, viewModel.getName())
            }
            permissionState.shouldShowRationale -> {
                Text(text = stringResource(id = R.string.camera_rationale))
            }
            permissionState.isPermanentlyDenied() -> {
                Text(text = stringResource(id = R.string.camera_permanently_denied))
            }
        }
    }
}