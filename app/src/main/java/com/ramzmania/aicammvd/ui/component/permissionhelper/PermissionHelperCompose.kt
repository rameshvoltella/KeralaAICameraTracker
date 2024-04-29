package com.ramzmania.aicammvd.ui.component.permissionhelper


import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import com.ramzmania.aicammvd.R
/**
 * Permission helper compose
 */
@ExperimentalPermissionsApi
@Composable
fun PermissionsHandler(
    permissions: List<String>,
    onPermissionsGranted: () -> Unit
) {
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)
    val context = LocalContext.current

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
//                    Text(text = "${perm.permission} permission is needed to use this feature")
                    ClickableText(
                        text = AnnotatedString("App wont work without location permission go to setting and enable location permission+\n Select AI Camera app -> Allow all the time"),
                        onClick = {
                            val settingsIntent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            )
                            settingsIntent.data = Uri.parse("package:" + context.packageName);

                            context.startActivity(settingsIntent)
                        },
                        style = TextStyle(
                            color = colorResource(
                                id = R.color.white_perment
                            ), fontSize = 16.sp
                        )
                    )

                    allPermissionsGranted = false
                }

                else -> {
//                    Text(text = "${perm.permission} permission is needed to use this feature")
                    ClickableText(
                        text = AnnotatedString("App wont work without location permission go to setting and enable location permission+\n Select AI Camera app -> Allow all the time"),
                        onClick = {
                            val settingsIntent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            )
                            settingsIntent.data = Uri.parse("package:" + context.packageName);
                            context.startActivity(settingsIntent)
                        },
                        style = TextStyle(
                            color = colorResource(
                                id = R.color.white_perment
                            ), fontSize = 16.sp
                        )
                    )

                    allPermissionsGranted = false
                }
            }
        }
        if (allPermissionsGranted) {
            onPermissionsGranted()
        }
    }
}