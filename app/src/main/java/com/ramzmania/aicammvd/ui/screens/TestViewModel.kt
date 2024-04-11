package com.ramzmania.aicammvd.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val screendata =MutableStateFlow("screeen1")
    fun  changeScreen(screendata: String)
    {
        this.screendata.value=screendata
    }

    val screenState=screendata.asStateFlow()

    private val aILocationLiveDataPrivate= MutableLiveData<Resource<CameraDataResponse>>()
    var counter = MutableLiveData<Int>(0)
    val aILocationLiveData : LiveData<Resource<CameraDataResponse>> get() = aILocationLiveDataPrivate
    val _items = mutableListOf<CameraDataResponse>()

    fun fetchAiLocationInfo()
    {
        viewModelScope.launch {

            aILocationLiveDataPrivate.value=Resource.Loading()

            localRepositorySource.requestCameraLocation().collect {
                aILocationLiveDataPrivate.value = it
            }
        }
    }
}