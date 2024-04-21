package com.ramzmania.aicammvd.ui.component.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.ui.customviews.CustomCircleSwitch
import com.ramzmania.aicammvd.utils.PermissionsHandler
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import android.provider.Settings
import com.ramzmania.aicammvd.utils.PreferencesUtil

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TrackerViewpagerItem(centerImage: Int, title: String, subtitle: String,enabledLocationValue:Boolean) {
    val context = LocalContext.current
   // , enabledLocation: (Boolean) -> Unit
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    var innerCircleSize by remember { mutableStateOf(140.dp) }
    var enableRememberLocation by remember { mutableStateOf(enabledLocationValue)}
    var innerColor by remember { mutableStateOf(R.color.red_demo) }
    var subtitleText by remember { mutableStateOf(subtitle) }
    var locationText by remember { mutableStateOf("subtitle") }
    val model = viewModel<HomeViewModel>()
    var showPermissionsDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var locationInfo=model.currentLocation.observeAsState()
    var locationAddress=model.locationAddressData.collectAsState()

    val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)
    val allPermissionsGranted = permissionsState.permissions.all { it.status.isGranted }
//    val kkpp:(Boolean)->Unit= model::updateLocationButton
//        val updateLocationData: (enableState:Boolean) -> Unit = model::updateLocationButton

//    kkpp(true)
    val stopFromNotification:Boolean=model.locationServiceStared.collectAsState().value


    LaunchedEffect(stopFromNotification) {
        // Collect from the a snapshotFlow reading the currentPage
      if(stopFromNotification)
      {
          model.updateLocationButton(false)
          enableRememberLocation = false

      }
    }
    LaunchedEffect(permissionsState) {
        showPermissionsDialog = !allPermissionsGranted
    }
    LaunchedEffect(key1 = enableRememberLocation )
    {
        if(enableRememberLocation)
        {

            innerCircleSize -= 5.dp
            innerColor=R.color.green_kelly_color
            subtitleText="Location : ON "
            locationText="Tracking Started From :"+ locationAddress.value+" updated @ "+PreferencesUtil.getString(context,"timer")

        }else
        {
            innerCircleSize =140.dp
            innerColor= R.color.red_demo
            subtitleText="Location : OFF"
            locationText=""+" updated @ "+PreferencesUtil.getString(context,"timer")


        }
    }
    if (showPermissionsDialog) {
        PermissionsHandler(
            permissions = permissions,
            onPermissionsGranted = {
                // handle permissions granted scenario
                showPermissionsDialog = false  // Reset the dialog visibility
            }
        )
    }else {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.brown_black))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)


                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(20.dp)
                    ) {
                        CustomCircleSwitch(outerCircleSize = 200.dp,
                            innerCircleSize = innerCircleSize,
                            colorResource(
                                id = R.color.circle_outer
                            ),
                            innerColor = colorResource(id = innerColor),
                            onClick =
                            {
                                if (ContextCompat.checkSelfPermission(
                                        context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) !=
                                    android.content.pm.PackageManager.PERMISSION_GRANTED) {
                                    showDialog = true
                                }else {


                                    //Toast.makeText(context,"yoooo",Toast.LENGTH_LONG).show()
                                    if (innerCircleSize == 140.dp) {

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                vibrator.vibrate(
                                                    VibrationEffect.createOneShot(
                                                        300,
                                                        VibrationEffect.DEFAULT_AMPLITUDE
                                                    )
                                                )
                                            } else {
                                                // For older versions of Android, you can just use vibrate()
                                                vibrator.vibrate(300)
                                            }

                                        }
                                        enableRememberLocation = true
                                        model.updateLocationButton(true)
                                        model.startLocationService(context,locationInfo.value)
                                    } else {
                                        enableRememberLocation = false
                                        model.updateLocationButton(false)
                                        model.stopLocationService(context)


                                    }
                                }
                            },
                            onLongPress = {
//                                Toast.makeText(context, "Lonf", Toast.LENGTH_LONG).show()
                                //   innerCircleSize = 10.dp

                            })

                    }
//            GlideImage(
//                painter = imagePainter,
//                contentDescription = title,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.padding(20.dp).width(100.dp).height(100.dp)
//            )

                    Text(
                        text = subtitleText, fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp),
                        color = colorResource(id = R.color.white_perment)

                    )

                    Text(
                        text = locationText,fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp),
                        color = colorResource(id = R.color.white_perment)

                    )


                }
//            if (currentPage == 2) {
//                Button(
//                    onClick = { /* Handle button click */ },
//                    modifier = Modifier
//                        .padding(16.dp) // Add padding to the button
//                        .align(Alignment.BottomEnd) // Align the button to the bottom end (bottom right)
//                ) {
//                    Text("Your Button Text")
//                }
//
//            }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Permission Required") },
            text = { Text("This app requires background location access to function properly. Please grant the permission.\n" +
                    "Select AI Camera app -> Allow all the time") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    val locationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    context.startActivity(locationIntent)
                }) {
                    Text("Grant Permission")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Dismiss")
                }
            }
        )
    }

}
@Preview(showBackground = true)
@Composable
fun TrackerViewpagerItemPreview() {
    TrackerViewpagerItem(
        centerImage = R.drawable.ic_livevideo_doubt, // Replace with actual drawable resource
        title = "Sample Title",
        subtitle = "Sample Subtitle",
        enabledLocationValue = true
    )
}
/*@Preview
@Composable
private fun ViewPagerItemPreview() {
//    TrackerViewpagerItem(R.drawable.cam_location, "kona", "intro", 2)
}*/
//@RequiresApi(Build.VERSION_CODES.O)
//fun vibrate() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
//    } else {
//        // For older versions of Android, you can just use vibrate()
//        vibrator.vibrate(100)
//    }
//}
