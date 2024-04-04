package com.ramzmania.aicammvd.ui.component.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.ui.component.cameralist.CameraListView
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BasicHomeLayer(dataCameraList: List<CameraData>) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    var currentPage by remember { mutableStateOf(0) }
    var selectedColumn by remember { mutableStateOf(0) }
    val scrollCoroutineScope = rememberCoroutineScope()
// Remember the scroll position for each page
    val scrollPositions = remember { mutableStateOf(mapOf<Int, LazyListState>()) }
    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedColumn=pagerState.currentPage

        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        // Place the bottom composable first


        // Place the top composable, ensuring it's above the red box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .align(Alignment.TopCenter)
                .background(colorResource(id = R.color.brown_black))
        ) {
            // Content of the HorizontalPager or any other composables
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
               userScrollEnabled = false
            ) {page->
                // Content of HorizontalPager
                if (page == 0) {
                    TrackerViewpagerItem(
                        centerImage = R.drawable.came_new,
                        title = "kona",
                        subtitle = "kokona",
                        currentPage = pagerState.currentPage
                    )
                } else {
                    CameraListView(cameralList = dataCameraList)
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(20.dp, 0.dp, 20.dp, 20.dp)
                .align(Alignment.BottomCenter)
                .border(
                    2.dp,
                    colorResource(id = R.color.white_border),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomEnd = 10.dp,
                        bottomStart = 10.dp
                    )
                )
                .background(colorResource(id = R.color.brown_black))
        ) {
            // Content of the red box
            Row(Modifier.fillMaxSize()) {
                Column(

                    Modifier
                        .weight(1f)
                        // .background(colorResource(id = R.color.red_demo))
                        .background(
                            if (selectedColumn == 0) colorResource(id = R.color.red_demo) else colorResource(
                                id = R.color.brown_black
                            )
                        )
                        .fillMaxHeight()
                        .clickable {
                            currentPage = 0
                            selectedColumn = 0
                            scrollCoroutineScope.launch {
                                pagerState.animateScrollToPage(0)
                            }
                        },
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "TRACKER", modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(), textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white_perment)
                    )
                }
                Column(
                    Modifier

                        .background(colorResource(id = R.color.red_demo))
                        .fillMaxHeight()
                        .width(3.dp),
                    verticalArrangement = Arrangement.Center,


                    ) {

                }
                Column(
                    Modifier
                        .weight(1f)
//                        .background(colorResource(id = R.color.brown_black))
                        .background(
                            if (selectedColumn == 1) colorResource(id = R.color.red_demo) else colorResource(
                                id = R.color.brown_black
                            )
                        )
                        .fillMaxHeight()
                        .clickable {
                            currentPage = 1
                            selectedColumn = 1
                            scrollCoroutineScope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        },
                    verticalArrangement = Arrangement.Center,


                    ) {
                    Text(
                        text = "LOCATION", modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(), textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white_perment)
                    )
                }
            }

        }
    }

}
@Composable
fun InitialLoadingScreen()
{
    Column(modifier =Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,


        ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Preview
@Composable
fun prev() {
//    InitialLoadingScreen()
//    BasicHomeLayer(dataCameraList)
}