package com.ramzmania.aicammvd.pager
/**
 * Calculates the current offset for the given page in a PagerState.
 * This function is experimental and requires opting in to the ExperimentalFoundationApi.
 *
 * @param page The page for which to calculate the offset.
 * @return The current offset for the given page.
 */
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}