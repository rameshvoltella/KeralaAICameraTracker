package com.ramzmania.aicammvd.ui.screens

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.data.local.LocalRepository
import com.ramzmania.aicammvd.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TestViewModel @Inject constructor(val localRepositorySource: LocalRepository) :BaseViewModel(){
    private val _count = MutableStateFlow(0)
    //    val count: Int = _count.value
    val count =_count.asStateFlow()

    fun incrementCount(value:Int) {
        _count.value=value
       // Log.d("changed","value"+_count)
    }
    private val _aILocationLiveDataPrivate = MutableStateFlow<Resource<CameraDataResponse>>(Resource.Loading())
    val aILocationLiveData: StateFlow<Resource<CameraDataResponse>> = _aILocationLiveDataPrivate

    fun fetchAiLocationInfo() {
        viewModelScope.launch {
            _aILocationLiveDataPrivate.value = Resource.Loading()
            localRepositorySource.requestCameraLocation().collect {
                _aILocationLiveDataPrivate.value = it
            }
        }
    }
}