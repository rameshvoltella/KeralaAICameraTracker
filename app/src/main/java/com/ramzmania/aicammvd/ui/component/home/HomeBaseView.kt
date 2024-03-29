package com.ramzmania.aicammvd.ui.component.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun basicHomeLayer() {
    val pagerState = rememberPagerState(pageCount = { 2 })
    Box(modifier = Modifier.fillMaxSize()) {
        // Place the bottom composable first

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(20.dp,0.dp,20.dp,20.dp)
                .align(Alignment.BottomCenter)
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp,
                        bottomEnd = 30.dp,
                        bottomStart = 30.dp
                    )
                )
                .background(Color.Red)
        ) {
            // Content of the red box
        }

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//                .background(Color.Red)
//                .padding(20.dp)
//                .align(Alignment.BottomCenter)
//                .clip(
//                    RoundedCornerShape(
//                        topStart = 30.dp,
//                        topEnd = 30.dp,
//                        bottomEnd = 30.dp,
//                        bottomStart = 30.dp
//                    )
//                )
//        ) {
//            // Content of the red box
//        }

        // Place the top composable, ensuring it's above the red box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(0.dp,0.dp,0.dp,100.dp)
                .align(Alignment.TopCenter)
                .background(Color.Green)
        ) {
            // Content of the HorizontalPager or any other composables
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) {
                // Content of HorizontalPager
            }
        }
    }

}

@Preview
@Composable
fun prev()
{
    basicHomeLayer()
}