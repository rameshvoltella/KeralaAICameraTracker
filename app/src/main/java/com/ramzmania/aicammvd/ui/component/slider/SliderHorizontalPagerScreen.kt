/**
 * HorizontalPagerWithLinesIndicatorScreen: A composable function that displays a horizontal pager
 * with pages containing slider items. It also includes a button on the last page to proceed to
 * the HomeActivity.
 *
 * @param dataList The list of SliderContentData objects representing the data to be displayed in each page.
 * @param activityContext The context of the activity where the HorizontalPagerWithLinesIndicatorScreen is used.
 */
package com.ramzmania.aicammvd.ui.component.slider

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.ramzmania.aicammvd.data.dto.slider.SliderContentData
import com.ramzmania.aicammvd.pager.calculateCurrentOffsetForPage
import com.ramzmania.aicammvd.ui.screens.home.HomeActivity
import com.ramzmania.aicammvd.utils.Constants
import com.ramzmania.aicammvd.utils.PreferencesUtil
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerWithLinesIndicatorScreen(dataList: List<SliderContentData>, activityContext: Activity) {

    val pagerState = rememberPagerState(pageCount = { dataList.size })
    val backgroundColor = when (pagerState.currentPage) {
        0 -> Color.White // Example color for page 0
        1 -> Color.Green // Example color for page 1
        2 -> Color("#00b4e9".toColorInt()) // Example color for page 2
        else -> MaterialTheme.colorScheme.background
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor // Updated MaterialTheme.colorScheme to MaterialTheme.colors
    ) {
        Box(modifier = Modifier) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

                HorizontalPager(state = pagerState) { page ->
                    Box(
                        Modifier
                            .pagerFadeTransition(page, pagerState)
                            .fillMaxSize()
                    ) {
                        SlideViewpagerItem(
                            dataList[page].imageResource, dataList[page].title,
                            dataList[page].subtitleData, pagerState.currentPage
                        )
                    }
                }

                if (pagerState.currentPage == 2) {
                    Button(
                        onClick = {
                            PreferencesUtil.setString(context = activityContext, Constants.SLIDER_SCREEN_PASSED,Constants.SLIDER_SCREEN_TAG)
                            activityContext.startActivity(Intent(activityContext, HomeActivity::class.java).addFlags(FLAG_ACTIVITY_NEW_TASK))
                            (activityContext as? Activity)?.finish() // Finish the current activity
                        },
                        modifier = Modifier
                            .padding(30.dp,30.dp,30.dp,60.dp) // Add padding to the button
                            .align(Alignment.BottomEnd) // Align the button to the bottom end (bottom right)
                    ) {
                        Text("Let's Track")
                    }

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
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.pagerFadeTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
        translationX = pageOffset * size.width
        alpha = 1 - pageOffset.absoluteValue
    }