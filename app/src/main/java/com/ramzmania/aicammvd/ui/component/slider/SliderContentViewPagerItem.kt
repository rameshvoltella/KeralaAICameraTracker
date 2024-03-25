package com.ramzmania.aicammvd.ui.component.slider

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ramzmania.aicammvd.R

@Composable
fun SlideViewpagerItem(centerImage: Int, imageDesc: String, introtxt: String) {
    val imagePainter = painterResource(id = centerImage)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(Color.Red)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "opening intro image $imageDesc",
                contentScale = ContentScale.Crop
            )
            Text(text = introtxt)
        }
    }
}
@Preview
@Composable
private fun ViewPagerItemPreview() {
    SlideViewpagerItem(R.drawable.ic_livevideo_doubt,"kona","intro")
}
