package com.ramzmania.aicammvd.utils


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@ExperimentalPermissionsApi
@Composable
fun PermissionsHandler(
    permissions: List<String>,
    onPermissionsGranted: () -> Unit
) {
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)


    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                permissionsState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var allPermissionsGranted = true
        permissionsState.permissions.forEach { perm ->
            when {
                perm.status.isGranted -> {
                    Text(text = "${perm.permission} permission accepted")
                }
                perm.status.shouldShowRationale -> {
                    Text(text = "${perm.permission} permission is needed to use this feature")
                    allPermissionsGranted = false
                }

                else -> {
                    Text(text = "${perm.permission} permission is needed to use this feature")
                    allPermissionsGranted = false
                }
            }
        }
        if (allPermissionsGranted) {
            onPermissionsGranted()
        }
    }
}