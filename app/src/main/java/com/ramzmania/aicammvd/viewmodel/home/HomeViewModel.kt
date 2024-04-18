package com.ramzmania.aicammvd.viewmodel.home

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ramzmania.aicammvd.data.ContextModule
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.data.local.LocalRepositorySource
import com.ramzmania.aicammvd.geofencing.removeAllGeofences
import com.ramzmania.aicammvd.service.AiCameraLocationUpdateService
import com.ramzmania.aicammvd.ui.base.BaseViewModel
import com.ramzmania.aicammvd.utils.LocationSharedFlow
import com.ramzmania.aicammvd.utils.PreferencesUtil
import com.ramzmania.aicammvd.workmanager.startAiServiceWorkManager
import com.ramzmania.aicammvd.workmanager.stopAiServiceWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel@Inject
constructor(private val localRepositorySource: LocalRepositorySource, private var contextModule: ContextModule
) : BaseViewModel() {

    private val aILocationLiveDataPrivate= MutableLiveData<Resource<CameraDataResponse>>()
    val aILocationLiveData : LiveData<Resource<CameraDataResponse>> get() = aILocationLiveDataPrivate

    private val locationEnabledPrivate = MutableStateFlow(false)
    val locationEnabled =locationEnabledPrivate.asStateFlow()

    private val locationServiceStoppedPrivate= MutableStateFlow(false)
    val  locationServiceStared=locationServiceStoppedPrivate.asStateFlow()

    private val locationDataPrivate = MutableLiveData<Location>()
    val locationData: LiveData<Location> = locationDataPrivate


    private val currentLocationDataPrivate = MutableLiveData<Location>()
    val currentLocation: LiveData<Location> = currentLocationDataPrivate

    private val setLayoutPrivate= MutableStateFlow(false)
    val setLayout=setLayoutPrivate.asStateFlow()

    private val filterCameraListDataPrivate = MutableLiveData<List<CameraData>>()
    val filterCameraList: LiveData<List<CameraData>> = filterCameraListDataPrivate

    init {
        viewModelScope.launch {
            LocationSharedFlow.locationFlow.collect { location ->

                locationDataPrivate.value = location
//                Log.d("Location Flow Update", "Lat: ${location.first}, Long: ${location.second}")
            }
        }

        viewModelScope.launch {
            LocationSharedFlow.serviceStopStatus.collect { status ->

                locationServiceStoppedPrivate.value = status
//                Log.d("Location Flow Update", "Lat: ${location.first}, Long: ${location.second}")
            }
        }
        locationEnabledPrivate.value=PreferencesUtil.isServiceRunning(contextModule.context)
    }
    fun updateLocationButton(value:Boolean) {
        locationEnabledPrivate.value=value

    }

    fun setTackingServiceRunning(isStarted:Boolean)
    {
     //   locationServiceStaredPrivate.value=isStarted
    }

    fun fetchAiLocationInfo()
    {
        viewModelScope.launch {

            aILocationLiveDataPrivate.value=Resource.Loading()

            localRepositorySource.requestCameraLocation().collect {
                aILocationLiveDataPrivate.value = it
            }
        }
    }

    fun startLocationService(context:Context) {
        Intent(context, AiCameraLocationUpdateService::class.java).also { intent ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            //setTackingServiceRunning(true)
        }
        startAiServiceWorkManager(context)
    }

    fun stopLocationService(context:Context) {
        removeAllGeofences(context)

        Intent(context, AiCameraLocationUpdateService::class.java).also { intent ->
            context.stopService(intent)
        }
       // setTackingServiceRunning(false)
        stopAiServiceWorkManager(context)

    }

    fun setCurrentLocation(location: Location) {
        currentLocationDataPrivate.value=location
    }

    fun setLayout(setLayout: Boolean) {
        setLayoutPrivate.value=setLayout
    }

    fun setFilteredCameraList(cameraList: List<CameraData>) {

        filterCameraListDataPrivate.value=cameraList
    }


}