package com.ramzmania.aicammvd.ui.component.slider

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramzmania.aicammvd.data.dto.slider.SliderContentData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerWithLinesIndicatorScreen(dataList: List<SliderContentData>) {

    val pagerState = rememberPagerState(pageCount = { dataList.size })

    Box(modifier = Modifier) {
        HorizontalPager(state = pagerState) { page ->
            SlideViewpagerItem(dataList[page].imageResource, dataList[page].subtitleData,
                dataList[page].title)
        }

        ViewPagerLinesIndicator(
            modifier = Modifier
                .height(24.dp)
                .padding(start = 4.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            pageCount = dataList.size,
            currentPageIteration = pagerState.currentPage
        )
    }
}