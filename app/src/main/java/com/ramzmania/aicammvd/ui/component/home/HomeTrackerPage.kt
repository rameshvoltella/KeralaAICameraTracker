package com.ramzmania.aicammvd.ui.component.home

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.ui.customviews.CustomCircleSwitch
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TrackerViewpagerItem(centerImage: Int, title: String, subtitle: String,enabledLocationValue:Boolean) {
    val context = LocalContext.current
   // , enabledLocation: (Boolean) -> Unit
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val imagePainter = painterResource(id = centerImage)
    var innerCircleSize by remember { mutableStateOf(140.dp) }
    var enableRememberLocation by remember { mutableStateOf(enabledLocationValue)}
    var innerColor by remember { mutableStateOf(R.color.red_demo) }
    var subtitleText by remember { mutableStateOf(subtitle) }
    val model = viewModel<HomeViewModel>()

    LaunchedEffect(key1 = enableRememberLocation )
    {
        if(enableRememberLocation)
        {

            innerCircleSize -= 5.dp
            innerColor=R.color.green_kelly_color
            subtitleText="Location : ON "

        }else
        {
            innerCircleSize =140.dp
            innerColor= R.color.red_demo
            subtitleText="Location : OFF"

        }
    }


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
                Text(text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp,0.dp,10.dp,0.dp)


                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(20.dp)
                ) {
                    CustomCircleSwitch(outerCircleSize = 200.dp, innerCircleSize = innerCircleSize, colorResource(
                        id = R.color.circle_outer
                    ), innerColor=colorResource(id = innerColor), onClick =
                    {
                        //Toast.makeText(context,"yoooo",Toast.LENGTH_LONG).show()
                        if(innerCircleSize==140.dp) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
                                } else {
                                    // For older versions of Android, you can just use vibrate()
                                    vibrator.vibrate(300)
                                }

                            }
                            enableRememberLocation=true
                            model.updateLocationData(true)
                        }else
                        {
                            enableRememberLocation=false
                            model.updateLocationData(false)


                        }

                    },
                        onLongPress = {
                            Toast.makeText(context,"Lonf",Toast.LENGTH_LONG).show()
                         //   innerCircleSize = 10.dp

                        })

                }
//            GlideImage(
//                painter = imagePainter,
//                contentDescription = title,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.padding(20.dp).width(100.dp).height(100.dp)
//            )

                Text(text = subtitleText, fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp,0.dp,10.dp,0.dp),
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

@Preview
@Composable
private fun ViewPagerItemPreview() {
//    TrackerViewpagerItem(R.drawable.cam_location, "kona", "intro", 2)
}
//@RequiresApi(Build.VERSION_CODES.O)
//fun vibrate() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
//    } else {
//        // For older versions of Android, you can just use vibrate()
//        vibrator.vibrate(100)
//    }
//}
