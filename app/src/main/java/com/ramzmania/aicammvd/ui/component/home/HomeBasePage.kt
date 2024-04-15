package com.ramzmania.aicammvd.ui.component.home

import android.Manifest
import android.location.Location
import android.util.Log
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.ui.component.cameralist.CameraListView
import com.ramzmania.aicammvd.utils.PermissionsHandler
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeLayer(viewModelStoreOwner: ViewModelStoreOwner, navigateTo: (route: String) -> Unit) {

    val pagerState = rememberPagerState(pageCount = { 2 })
    var currentPage by remember { mutableStateOf(0) }
    var selectedColumn by remember { mutableStateOf(0) }
    val scrollCoroutineScope = rememberCoroutineScope()
    var dataCameraList: List<CameraData>? = null
    var nearestHundredCameras:List<CameraData>?=null
    val model = viewModel<HomeViewModel>(viewModelStoreOwner = viewModelStoreOwner)
    val aiLocationInfo by model.aILocationLiveData.observeAsState(Resource.Loading())
    val updateLocationData: (enableState:Boolean) -> Unit = model::updateLocationData

    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedColumn = pagerState.currentPage

        }
    }


//    if (isLoading) {
//    CircularProgressIndicator(modifier = Modifier.fillMaxSize(), strokeWidth = 8.dp)
    LaunchedEffect(key1 = aiLocationInfo) {
        when (aiLocationInfo) {
            is Resource.Loading -> {
                // Show loading indicator
                Log.d("tadada", "came1")
//                        model.incrementCount(400000)

            }

            is Resource.Success -> {
                Log.d("tadada", "came2")

//                    dataCameraList = aiLocationInfo.data?.responseList
                //isLoading = false

            }

            is Resource.DataError -> {
                // Handle error
                // For example:
                // val errorCode = resource.errorCode
                Log.d("tadada", "cameerrr")

            }

            else -> {}
        }
    }


    LaunchedEffect(key1 = Unit) {

        model.fetchAiLocationInfo()
    }

    // UI based on aiLocationInfo
    Box(modifier = Modifier.fillMaxSize()) {
        when (aiLocationInfo) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 8.dp
                )
            }

            is Resource.Success -> {
//                Toast.makeText(LocalContext.current,"Tadada",1).show()
                //Text("Sucess", modifier = Modifier.align(Alignment.Center))
//                    dataCameraList = aiLocationInfo.data?.responseList
                    dataCameraList = aiLocationInfo.data?.responseList
                    nearestHundredCameras = dataCameraList?.findNearestCameras(9.759041581724828, 76.4833893696677)

//                Box(modifier = Modifier.fillMaxSize()) {
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
                        ) { page ->
                            // Content of HorizontalPager
                            if (page == 0) {
                                TrackerViewpagerItem(
                                    centerImage = R.drawable.came_new,
                                    title = "Track AI Camera",
                                    subtitle = "Location  : OFF",
                                    enabledLocationValue=model.locationEnabled.value,
                                )
                            } else {
                                CameraListView(cameralList = nearestHundredCameras!!)
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
//                }


            }

            is Resource.DataError -> {
                Text("Failed to load data", modifier = Modifier.align(Alignment.Center))
            }

            else -> Unit // Handle other states, if necessary
        }
    }

}

fun List<CameraData>.findNearestCameras(currentLat: Double, currentLong: Double): List<CameraData> {
    val currentLocation = Location("").apply {
        latitude = currentLat
        longitude = currentLong
    }

    return sortedBy {
        Location("").apply {
            latitude = it.latitude
            longitude = it.longitude
        }.distanceTo(currentLocation)
    }.take(100)
}


