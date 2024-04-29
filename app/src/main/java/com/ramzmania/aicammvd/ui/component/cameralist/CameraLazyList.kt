package com.ramzmania.aicammvd.ui.component.cameralist

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.ui.screens.mapview.OsmMapActivity

/**
 * CameraListView: A composable function responsible for rendering a list of camera data.
 * It displays the nearest AI camera locations and allows the user to click on them to view
 * the details on the map.
 */
@Composable
fun CameraListView(
    modifier: Modifier = Modifier,
    cameraList: List<CameraData>
) {
    val context = LocalContext.current
//    val listState = remember { LazyListState() }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Add your TextView here
        Text(
            text = "NEAREST AI CAMERA",
            color= colorResource(id = R.color.white_perment),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        LazyColumn(modifier = modifier.padding(vertical = 4.dp), state = rememberLazyListState()) {
            items(items = cameraList) { list ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.circle_outer)
                    ),
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clickable {
                            val intent = Intent(context, OsmMapActivity::class.java)
                            // Optionally add data to your intent
                            intent.putExtra("lat", list.latitude)
                            intent.putExtra("long", list.longitude)
                            context.startActivity(intent)
                        }
                ) {
                    CameraLayoutList(list.district, list.location)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewList()
{
    val dummyCameraList = listOf(
        CameraData("District 1", "Location 1","Location 1",1.0,  1.0,"kona"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),
        CameraData("District 2", "Location 2","Location 1", 1.0,1.0,"kona2"),

        // Add more dummy CameraData objects as needed
    )
    CameraListView(cameraList = dummyCameraList)
}