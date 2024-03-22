package com.ramzmania.aicammvd.ui.compose.slider

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!", fontSize = 24.sp)
    }
@Composable
fun ComposeArticle(head: String, body1: String, body2: String, image: Int) {
    val image = painterResource(id = image)
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = image,
            contentDescription = "Jetpack Compose Article",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = head,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
        )
        Text(
            text = body1,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp)
        )
        Text(
            text = body2,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 16.dp)
        )
    }
}

