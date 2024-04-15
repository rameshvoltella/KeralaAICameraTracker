package com.ramzmania.aicammvd.viewmodel.home

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.data.local.LocalRepositorySource
import com.ramzmania.aicammvd.service.AiCameraLocationUpdateService
import com.ramzmania.aicammvd.ui.base.BaseViewModel
import com.ramzmania.aicammvd.utils.LocationSharedFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel@Inject
constructor(private val localRepositorySource: LocalRepositorySource
) : BaseViewModel() {

    private val aILocationLiveDataPrivate= MutableLiveData<Resource<CameraDataResponse>>()
    val aILocationLiveData : LiveData<Resource<CameraDataResponse>> get() = aILocationLiveDataPrivate

    private val locationEnabledPrivate = MutableStateFlow(false)
    val locationEnabled =locationEnabledPrivate.asStateFlow()
    private val locationServiceStaredPrivate= MutableStateFlow(false)
    val  locationServiceStared=locationServiceStaredPrivate.asStateFlow()

    private val locationDataPrivate = MutableLiveData<Location>()
    val locationData: LiveData<Location> = locationDataPrivate

    init {
        viewModelScope.launch {
            LocationSharedFlow.locationFlow.collect { location ->

                locationDataPrivate.value = location
//                Log.d("Location Flow Update", "Lat: ${location.first}, Long: ${location.second}")
            }
        }
    }
    fun updateLocationData(value:Boolean) {
        locationEnabledPrivate.value=value
    }

    fun setTackingServiceRunning(isStarted:Boolean)
    {
        locationServiceStaredPrivate.value=isStarted
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
            setTackingServiceRunning(true)
        }
    }

    fun stopLocationService(context:Context) {
        Intent(context, AiCameraLocationUpdateService::class.java).also { intent ->
            context.stopService(intent)
        }
        setTackingServiceRunning(false)

    }


}