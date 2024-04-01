package com.ramzmania.aicammvd.ui.component.home

import CustomCircle2
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.ui.customviews.CustomCircleSwitch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TrackerViewpagerItem(centerImage: Int, title: String, subtitle: String, currentPage: Int) {
    val context = LocalContext.current
    val imagePainter = painterResource(id = centerImage)
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
                    modifier = Modifier.wrapContentWidth().padding(20.dp)
                ) {
                    CustomCircleSwitch(outerCircleSize = 200.dp, innerCircleSize = 140.dp, colorResource(
                        id = R.color.circle_outer
                    ), colorResource(id = R.color.red_demo), onClick =
                    {
                        Toast.makeText(context,"yoooo",Toast.LENGTH_LONG).show()
                    },
                        onLongPress = {
                            Toast.makeText(context,"Lonf",Toast.LENGTH_LONG).show()

                        })

                }
//            GlideImage(
//                painter = imagePainter,
//                contentDescription = title,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.padding(20.dp).width(100.dp).height(100.dp)
//            )

                Text(text = subtitle, fontSize = 16.sp,
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
    TrackerViewpagerItem(R.drawable.cam_location, "kona", "intro", 2)
}
