package com.ramzmania.aicammvd.ui.component.cameralist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramzmania.aicammvd.R


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun CameraLayoutList( district:String,place:String)
{
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight().align(Alignment.Center)
            ) {
                Row {
//                    GlideImage(
//                        model =  R.drawable.cam_location,
//                        contentDescription = "lola",
//                        modifier = Modifier
//                            .padding(20.dp)
//                            .height(20.dp)
//                            .width(20.dp)
//                            .align(Alignment.CenterVertically)
//                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.red_location)
                           // .crossfade(true)
                            .build(),
                        contentDescription = "Drawable Images",
                        modifier = Modifier
                            .padding(20.dp)
                            .height(20.dp)
                            .width(20.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterVertically)


                    ) {
                        Text(
                            text = district, modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(), textAlign = TextAlign.Left,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.white_perment)
                        )
                        Text(
                            text = place, modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(), textAlign = TextAlign.Left,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.white_perment)
                        )

                    }

                }
            }


        }
    }
}


@Preview
@Composable
private fun previewItem() {
//    val namesList = remember { List(100) { "$it" } }

    //CameraListView(names = namesList)
}



