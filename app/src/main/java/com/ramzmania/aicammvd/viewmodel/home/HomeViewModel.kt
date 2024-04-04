package com.ramzmania.aicammvd.viewmodel.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.data.local.LocalRepositorySource
import com.ramzmania.aicammvd.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel@Inject
constructor(private val localRepositorySource: LocalRepositorySource
) : BaseViewModel() {

    private val aILocationLiveDataPrivate= MutableLiveData<Resource<CameraDataResponse>>()
    var counter =MutableLiveData<Int>(0)
    val aILocationLiveData : LiveData<Resource<CameraDataResponse>> get() = aILocationLiveDataPrivate
     val _items = mutableListOf<CameraDataResponse>()
    private val _count = mutableStateOf(0)
    val count: Int = _count.value
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