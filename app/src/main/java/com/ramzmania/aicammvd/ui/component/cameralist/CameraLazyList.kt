package com.ramzmania.aicammvd.ui.component.cameralist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.colorResource
import com.ramzmania.aicammvd.R

@Composable
 fun CameraListView(
    modifier: Modifier = Modifier.padding(0.dp,20.dp,0.dp,90.dp),
    names: List<String> = List(100) { "$it" }
) {
    LazyColumn(modifier = modifier
        .padding(vertical = 4.dp)
        .background(colorResource(id = R.color.brown_black))) {
        items(items = names) { name ->
            CameraLayoutList(name)
        }
    }
}