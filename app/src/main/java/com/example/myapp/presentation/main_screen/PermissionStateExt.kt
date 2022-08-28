package com.example.myapp.presentation.main_screen

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentlyDenied() : Boolean {
    return !shouldShowRationale && !hasPermission
}