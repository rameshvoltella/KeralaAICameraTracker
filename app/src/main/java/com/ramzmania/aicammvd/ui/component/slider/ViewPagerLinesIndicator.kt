/**
 * ViewPagerLinesIndicator: A composable function that displays a set of lines indicating the current page
 * in a ViewPager-like UI.
 *
 * @param modifier The modifier for the lines indicator.
 * @param pageCount The total number of pages.
 * @param currentPageIteration The index of the current page.
 */
package com.ramzmania.aicammvd.ui.component.slider

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ViewPagerLinesIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPageIteration: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->

            val color = if (currentPageIteration == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(color)
//                    .weight(1f)
                    .width(20.dp)
                    .height(4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ViewPagerLinesIndicatorPreview() {
    ViewPagerLinesIndicator(pageCount = 2, currentPageIteration = 2)
}