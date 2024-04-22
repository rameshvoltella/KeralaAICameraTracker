package com.ramzmania.aicammvd.ui.component.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.geofencing.findNearestCameras
import com.ramzmania.aicammvd.ui.component.cameralist.CameraListView
import com.ramzmania.aicammvd.utils.PermissionsHandler
import com.ramzmania.aicammvd.utils.PreferencesUtil
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeLayer(viewModelStoreOwner: ViewModelStoreOwner, navigateTo: (route: String) -> Unit) {

    val pagerState = rememberPagerState(pageCount = { 2 })
    var currentPage by remember { mutableStateOf(0) }
    var selectedColumn by remember { mutableStateOf(0) }
    val scrollCoroutineScope = rememberCoroutineScope()
    var dataCameraList: List<CameraData>? = null
    val currentContext = LocalContext.current
    val model = viewModel<HomeViewModel>(viewModelStoreOwner = viewModelStoreOwner)
    val aiLocationInfo by model.aILocationLiveData.observeAsState(Resource.Loading())
    val currentLocation = model.currentLocation.observeAsState().value
    var dataLoaded by remember { mutableStateOf(false) }
    var locationNotAvailable by remember { mutableStateOf(false) }
    val setlayoutLayer = model.setLayout.collectAsState().value
    val setNoLocationLayout = model.setNoLocationData.collectAsState().value
    val nearestHundredCamerasList = model.filterCameraList.observeAsState().value
    var showPermissionsDialog by remember { mutableStateOf(false) }
    val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)
    val allPermissionsGranted = permissionsState.permissions.all { it.status.isGranted }
//    val updateLocationData: (enableState:Boolean) -> Unit = model::updateLocationButton
//    val stopFromService:Boolean=model.locationEnabled.collectAsState().value
    LaunchedEffect(permissionsState) {
        showPermissionsDialog = !allPermissionsGranted
    }

    if (showPermissionsDialog) {
        PermissionsHandler(
            permissions = permissions,
            onPermissionsGranted = {
                // handle permissions granted scenario
                showPermissionsDialog = false  // Reset the dialog visibility
            }
        )
    } else {
        LaunchedEffect(pagerState) {
            // Collect from the a snapshotFlow reading the currentPage
            snapshotFlow { pagerState.currentPage }.collect { page ->
                selectedColumn = pagerState.currentPage

            }
        }

        LaunchedEffect(dataLoaded) {
            // Collect from the a snapshotFlow reading the currentPage
            if (dataLoaded) {
                model.setLayout(true)
            }
        }

        LaunchedEffect(locationNotAvailable) {
            // Collect from the a snapshotFlow reading the currentPage

            if (locationNotAvailable) {
                model.setNoLocation(true)
            }
        }


//    if (isLoading) {
//    CircularProgressIndicator(modifier = Modifier.fillMaxSize(), strokeWidth = 8.dp)
        LaunchedEffect(key1 = aiLocationInfo) {

            when (aiLocationInfo) {
                is Resource.Loading -> {
                    // Show loading indicator
//                        model.incrementCount(400000)

                }

                is Resource.Success -> {
                    dataCameraList = aiLocationInfo.data?.responseList

//                    val nearestHundredCameras = dataCameraList?.findNearestCameras(
//                        currentLocation!!.latitude,
//                        currentLocation.longitude
//                    )
                    model.setFilteredCameraList(dataCameraList!!)
                    dataLoaded = true
//                    dataCameraList = aiLocationInfo.data?.responseList
                    //isLoading = false

                }

                is Resource.DataError -> {
                    // Handle error
                    // For example:
                    // val errorCode = resource.errorCode

                }

                else -> {}
            }
        }


        LaunchedEffect(key1 = Unit) {
            if (currentLocation == null) {
                val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                    currentContext
                )

                if (ActivityCompat.checkSelfPermission(
                        currentContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationProviderClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            if (location != null) {
                                model.setCurrentLocation(location)
                                model.fetchAiLocationInfo()
                            } else {
                                Toast.makeText(
                                    currentContext,
                                    "Location not available",
                                    Toast.LENGTH_LONG
                                ).show()
                                if(!PreferencesUtil.isServiceRunning(context = currentContext)) {
                                    locationNotAvailable = true
                                }
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                currentContext,
                                "Failed to get location",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            if(!PreferencesUtil.isServiceRunning(context = currentContext)) {
                                locationNotAvailable = true
                            }
                        }
                }
//        Toast.makeText(currentContext, "Failed to get location", Toast.LENGTH_LONG).show()
//        model.fetchAiLocationInfo()
            }
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

                }

                is Resource.DataError -> {
                    Text("Failed to load data", modifier = Modifier.align(Alignment.Center))
                }

                else -> Unit // Handle other states, if necessary
            }
        }
        if (setNoLocationLayout) {
            Box(modifier = Modifier.fillMaxSize()) {
                LocationNotAvailableMessage()
            }

            } else if (setlayoutLayer) {
            Box(modifier = Modifier.fillMaxSize()) {
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
                                enabledLocationValue = model.locationEnabled.value,
                            )
                        } else {
                            CameraListView(cameralList = nearestHundredCamerasList!!)
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
    }


}




