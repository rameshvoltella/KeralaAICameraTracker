/**
 * SlideViewpagerItem: A composable function that represents an item in a ViewPager.
 * It displays a title, subtitle, and an image centered vertically and horizontally.
 *
 * @param centerImage The resource ID of the image to be displayed.
 * @param title The title text to be displayed.
 * @param subtitle The subtitle text to be displayed.
 * @param currentPage The current page index.
 */
package com.ramzmania.aicammvd.ui.component.slider

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
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramzmania.aicammvd.R

@Composable
fun SlideViewpagerItem(centerImage: Int, title: String, subtitle: String, currentPage: Int) {
//    val imagePainter = painterResource(id = centerImage)
    val context= LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
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
                    modifier = Modifier.padding(10.dp,0.dp,10.dp,0.dp),
                    fontFamily = FontFamily(
                        typeface = ResourcesCompat.getFont(context, R.font.font_bold)!!
                    )

                )

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(centerImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Drawable Image",
                    modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp)
                        .width(200.dp),
                    contentScale = ContentScale.Crop
                )
//            GlideImage(
//                painter = imagePainter,
//                contentDescription = title,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.padding(20.dp).width(100.dp).height(100.dp)
//            )

                Text(text = subtitle,fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp,0.dp,10.dp,0.dp),
                            fontFamily = FontFamily(
                            typeface = ResourcesCompat.getFont(context, R.font.font_regular)!!
                            )
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
    SlideViewpagerItem(R.drawable.cam_location, "kona", "intro", 2)
}
