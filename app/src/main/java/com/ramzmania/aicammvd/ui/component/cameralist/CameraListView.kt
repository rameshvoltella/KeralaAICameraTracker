package com.ramzmania.aicammvd.ui.component.cameralist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
fun cameraLayoutList()
{
    Box {
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight().
        align(Alignment.Center)) {
            Row {
                Image(painter = painterResource(id = R.drawable.cam_location),
                    contentDescription ="lola" ,
                    modifier = Modifier
                        .padding(20.dp)
                        .height(20.dp)
                        .width(20.dp)
                        .align(Alignment.CenterVertically)
                )
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.CenterVertically)


                ) {
                    Text(
                        text = "Track", modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(), textAlign = TextAlign.Left,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white_perment)
                    )
                    Text(text = "cor,",  modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(), textAlign = TextAlign.Left,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.white_perment))

                }

            }
        }


    }

}

@Preview
@Composable
private fun previewItem() {
    cameraLayoutList()
}
