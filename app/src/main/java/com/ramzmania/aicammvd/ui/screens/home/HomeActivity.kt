package com.ramzmania.aicammvd.ui.screens.home

import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.navigation.HomeNavigation
import com.ramzmania.aicammvd.ui.theme.AiCameraApplicationTheme
import com.ramzmania.aicammvd.utils.observe
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseComposeActivity<HomeViewModel>() {

    // Declare NavHostController
    override fun getViewModelClass() = HomeViewModel::class.java

    override fun observeViewModel() {
//        observe(viewModel.aILocationLiveData, ::handleResponse)

    }

/*
    private fun handleResponse(response: Resource<CameraDataResponse>) {
        when (response) {
            is Resource.Loading -> {
                Log.d("PREPEP","loading")
            }
            is Resource.Success -> {
                isLoading.value=false
                Log.d("PREPEP","fkedup success"+response.data?.responseList?.get(0)?.district)
//                (navController = ).navigate(NavGraph.HOME_SCREEN)
//                navController.navigate(NavGraph.HOME_SCREEN) // Navigate to BasicHomeLayer()
//                navController.navigate(NavGraph.HOME_SCREEN) {
//                    popUpTo(NavGraph.INITIAL_SCREEN) {
//                        inclusive = false
//                    }
//                }
               // isLoading = false
                dataCameraList = response.data?.responseList
            }
            is Resource.DataError -> {
                Log.d("PREPEP","fkedup dataerror")

            }
            else -> {
                Log.d("PREPEP","fkedup else")
            }
        }

    }
*/

    override fun observeActivity() {

    }

    override fun beforeOnContent() {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
//        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.light(
//                Color.Transparent, Color.Transparent
//            ),
//            navigationBarStyle = SystemBarStyle.light(
//                Color.Transparent, Color.Transparent
//            )
//        )
    }

    @Composable
    override fun setContent() {
        AiCameraApplicationTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
            ) {

                HomeNavigation()




            }
        }


    }
    fun callApi() {
        viewModel.fetchAiLocationInfo()
    }


}