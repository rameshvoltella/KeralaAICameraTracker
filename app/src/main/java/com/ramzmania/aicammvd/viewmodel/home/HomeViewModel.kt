package com.ramzmania.aicammvd.viewmodel.home

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.boardcast.NotificationDismissReceiver
import com.ramzmania.aicammvd.boardcast.homePagePendingIntent
import com.ramzmania.aicammvd.data.ContextModule
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.data.local.LocalRepositorySource
import com.ramzmania.aicammvd.geofencing.getCompleteAddressString
import com.ramzmania.aicammvd.geofencing.playNotificationSound
import com.ramzmania.aicammvd.geofencing.removeAllGeofences
import com.ramzmania.aicammvd.ui.base.BaseViewModel
import com.ramzmania.aicammvd.ui.screens.home.HomeActivity
import com.ramzmania.aicammvd.utils.Constants
import com.ramzmania.aicammvd.utils.Constants.LOCATION_WORK_MANAGER_TAG
import com.ramzmania.aicammvd.utils.Constants.NOTIFY_TRACKING_SUBTITLE
import com.ramzmania.aicammvd.utils.Constants.NOTIFY_TRACKING_TITLE
import com.ramzmania.aicammvd.utils.LocationSharedFlow
import com.ramzmania.aicammvd.utils.Logger
import com.ramzmania.aicammvd.utils.NotificationUtil
import com.ramzmania.aicammvd.utils.PreferencesUtil
import com.ramzmania.aicammvd.workmanager.LocationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(
    private val localRepositorySource: LocalRepositorySource,
    private var contextModule: ContextModule
) : BaseViewModel() {

    private val aILocationLiveDataPrivate = MutableLiveData<Resource<CameraDataResponse>>()
    val aILocationLiveData: LiveData<Resource<CameraDataResponse>> get() = aILocationLiveDataPrivate

    private val locationEnabledPrivate = MutableStateFlow(false)
    val locationEnabled = locationEnabledPrivate.asStateFlow()

    private val locationServiceStoppedPrivate = MutableStateFlow(false)
    val locationServiceStared = locationServiceStoppedPrivate.asStateFlow()

    private val locationDataPrivate = MutableLiveData<Location>()
    val locationData: LiveData<Location> = locationDataPrivate

    private val locationAddressPrivate = MutableStateFlow("")
    val locationAddressData = locationAddressPrivate.asStateFlow()


    private val currentLocationDataPrivate = MutableLiveData<Location>()
    val currentLocation: LiveData<Location> = currentLocationDataPrivate

    private val setLayoutPrivate = MutableStateFlow(false)
    val setLayout = setLayoutPrivate.asStateFlow()

    private val setNoLocationPrivate = MutableStateFlow(false)
    val setNoLocationData = setNoLocationPrivate.asStateFlow()

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
        locationEnabledPrivate.value = PreferencesUtil.isServiceRunning(contextModule.context)
    }

    fun updateLocationButton(value: Boolean) {
        locationEnabledPrivate.value = value

    }

    fun setTackingServiceRunning(isStarted: Boolean) {
        //   locationServiceStaredPrivate.value=isStarted
    }

    fun fetchAiLocationInfo() {
        viewModelScope.launch {

            aILocationLiveDataPrivate.value = Resource.Loading()

            localRepositorySource.requestCameraLocation().collect {
                aILocationLiveDataPrivate.value = it
            }
        }
    }

    fun startLocationService(context: Context, location: Location?) {

        if (location != null) {

            viewModelScope.launch {


                localRepositorySource.setNewAiCameraCircle(location.latitude, location.longitude)
                    .collect {
                        if (it.data == true) {
                            settingWorkPeriodicScheduler(context)

                        }
                    }

            }

        }
    }

    private fun settingWorkPeriodicScheduler(context: Context) {
        setAiTracker(context)
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(
                LocationWorker::class.java,
                15,
                TimeUnit.MINUTES
            )
                .addTag(LOCATION_WORK_MANAGER_TAG) // Adding a tag to the work request
                .build()

        // Enqueue the work
        WorkManager.getInstance(context).enqueue(periodicWorkRequest)
        playNotificationSound(context, R.raw.loco)
    }

    private fun setAiTracker(context: Context) {


        NotificationUtil.createNotificationChannel(
            context, Constants.CHANNEL_ID,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        NotificationUtil.showNotification(
            context, NOTIFY_TRACKING_TITLE, NOTIFY_TRACKING_SUBTITLE, homePagePendingIntent(context),
            R.drawable.red_location,
            NotificationCompat.PRIORITY_HIGH,
            Constants.FAKE_SERVICE_NOTIFICATION_ID,
            Constants.CHANNEL_ID, false
        )
        locationEnabledPrivate.value = true

        PreferencesUtil.setServiceRunning(context, true)


    }

    fun stopLocationService(context: Context) {
        PreferencesUtil.setServiceRunning(context, false)

        removeAllGeofences(context)

//        Intent(context, AiCameraLocationUpdateService::class.java).also { intent ->
//            context.stopService(intent)
//        }
//       // setTackingServiceRunning(false)
//        stopAiServiceWorkManager(context)
        WorkManager.getInstance(context).cancelAllWorkByTag(LOCATION_WORK_MANAGER_TAG)
        locationEnabledPrivate.value = false
        try {

            val buttonIntent = Intent(context, NotificationDismissReceiver::class.java)
            val buttonPendingIntent = PendingIntent.getBroadcast(
                context,
                209,
                buttonIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            buttonPendingIntent.send()
        } catch (e: PendingIntent.CanceledException) {
            Logger.e("PendingIntent cancelled", e)
        }


    }

    fun setCurrentLocation(location: Location) {
        currentLocationDataPrivate.value = location
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                getCompleteAddressString(contextModule.context,location.latitude,location.longitude)

            }

            LocationSharedFlow.geocoderAddressData.collect {
                it?.let {
                    locationAddressPrivate.value = it
                }

            }
        }

    }

    fun setLayout(setLayout: Boolean) {
        setLayoutPrivate.value = setLayout
    }
    fun setNoLocation(setLayout: Boolean) {
        setNoLocationPrivate.value = setLayout
    }

    fun setFilteredCameraList(cameraList: List<CameraData>) {

        filterCameraListDataPrivate.value = cameraList
    }


}