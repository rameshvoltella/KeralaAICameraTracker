package com.ramzmania.aicammvd.ui.screens.home

import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.component.home.BasicHomeLayer
import com.ramzmania.aicammvd.ui.theme.AiCameraApplicationTheme
import com.ramzmania.aicammvd.utils.observe
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseComposeActivity<HomeViewModel>() {
    override fun getViewModelClass() = HomeViewModel::class.java

    override fun observeViewModel() {
        observe(viewModel.aILocationLiveData, ::handleResponse)

    }

    private fun handleResponse(response: Resource<CameraDataResponse>) {
        when (response) {
            is Resource.Loading -> {
                Log.d("PREPEP","loading")
            }
            is Resource.Success -> {
                Log.d("PREPEP","fkedup success"+response.data?.responseList?.get(0)?.district)


            }
            is Resource.DataError -> {
                Log.d("PREPEP","fkedup dataerror")

            }
            else -> {                Log.d("PREPEP","fkedup else")
            }
        }

    }

    override fun observeActivity() {
        callApi()
    }

    override fun beforeOnContent() {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))

    }

    @Composable
    override fun setContent() {

        AiCameraApplicationTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
            ) {

                BasicHomeLayer()
            }
        }


    }
    fun callApi() {
        viewModel.fetchAiLocationInfo()
    }

}