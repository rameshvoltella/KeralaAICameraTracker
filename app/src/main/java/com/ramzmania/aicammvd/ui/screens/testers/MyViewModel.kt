package com.ramzmania.aicammvd.ui.screens.testers

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.data.local.LocalRepositorySource
import com.ramzmania.aicammvd.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyViewModel @Inject
constructor(private val localRepositorySource: LocalRepositorySource
) : BaseViewModel()  {
//    var data by MutableStateFlow("Initial Value")
//        private set
//
//    fun updateData(newValue: String) {
//        data = newValue
//    }

    private val data = MutableStateFlow("Initial Value")
    //    val count: Int = _count.value
    val count =data.asStateFlow()

    fun updateData(value:String) {
        data.value=value
        // Log.d("changed","value"+_count)
    }

    private val aILocationLiveDataPrivate= MutableLiveData<Resource<CameraDataResponse>>()
    var counter = MutableLiveData<Int>(0)
//    val aILocationLiveData : LiveData<Resource<CameraDataResponse>> get() = aILocationLiveDataPrivate
    val _items = mutableListOf<CameraDataResponse>()
    private val _aILocationLiveDataPrivate = MutableStateFlow<Resource<CameraDataResponse>>(Resource.Loading())
    val aILocationLiveData: StateFlow<Resource<CameraDataResponse>> = _aILocationLiveDataPrivate

//    fun fetchAiLocationInfo()
//    {
//        viewModelScope.launch {
//
//            aILocationLiveDataPrivate.value= Resource.Loading()
//
//            localRepositorySource.requestCameraLocation().collect {
//                aILocationLiveDataPrivate.value = it
//            }
//        }
//    }
fun fetchAiLocationInfo() {
    viewModelScope.launch {
        _aILocationLiveDataPrivate.value = Resource.Loading()
        localRepositorySource.requestCameraLocation().collect {
            _aILocationLiveDataPrivate.value = it
        }
    }
}
}