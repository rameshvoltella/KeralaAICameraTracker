package com.ramzmania.aicammvd.ui.component.cameralist

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.ui.screens.mapview.OsmMapActivity


@Composable
fun CameraListView(
    modifier: Modifier = Modifier,
    cameralList: List<CameraData>
) {
    val context = LocalContext.current
//    val listState = remember { LazyListState() }
    LazyColumn(modifier = modifier.padding(vertical = 4.dp),state = rememberLazyListState()) {
        items(items = cameralList) { list ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.circle_outer)
                ),
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp).clickable {
                    val intent = Intent(context, OsmMapActivity::class.java)
                    // Optionally add data to your intent
                    intent.putExtra("lat", list.latitude)
                    intent.putExtra("long", list.longitude)
                    context.startActivity(intent)
                }
            ) {
                CameraLayoutList(list.district,list.location)
            }
        }
    }
}