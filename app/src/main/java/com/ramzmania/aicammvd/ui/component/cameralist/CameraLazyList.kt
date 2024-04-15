package com.ramzmania.aicammvd.ui.component.cameralist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData


@Composable
fun CameraListView(
    modifier: Modifier = Modifier,
    cameralList: List<CameraData>
) {
//    val listState = remember { LazyListState() }
    LazyColumn(modifier = modifier.padding(vertical = 4.dp),state = rememberLazyListState()) {
        items(items = cameralList) { list ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.circle_outer)
                ),
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            ) {
                CameraLayoutList(list.district,list.location)
            }
        }
    }
}