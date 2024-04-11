package com.ramzmania.aicammvd.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramzmania.aicammvd.data.Resource

@Composable
fun ScreenThree(count: Int, navigateTo: (route: String) -> Unit)
{
//    val testViewModel = viewModel<TestViewModel>()

//    val count by testViewModel.count.collectAsState()
    val model = viewModel<TestViewModel>()
    val aiLocationInfo by model.aILocationLiveData.observeAsState(Resource.Loading())

    Column {
        Button(onClick = {
            model.fetchAiLocationInfo()
            aiLocationInfo?.let { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Show loading indicator
                        Log.d("tadada","came1")
//                        model.incrementCount(400000)

                    }
                    is Resource.Success -> {
                        // Handle success
                        // Navigate to home screen
                        // Access your data from resource.data
                        // For example:
                        // val cameraDataResponse = resource.data
                        Log.d("tadada","came"+resource.data?.responseList?.get(0)?.district)
//                        navigateTo("home")
                        model.incrementCount(3000)
                        model.changeScreen("screen 3")

//                        navigateTo("scr3")
                    }
                    is Resource.DataError -> {
                        // Handle error
                        // For example:
                        // val errorCode = resource.errorCode
                        Log.d("tadada","cameerrr")

                    }
                    is Resource.LoadingInstance -> {
                        // Handle loading with instanceIdentifier
                        // For example:
                        // val instanceIdentifier = resource.instanceIdentifier
                        Log.d("tadada","came333")

                    }
                    is Resource.SuccessInstance -> {
                        // Handle success with instanceIdentifier
                        // For example:
                        // val data = resource.data
                        // val instanceIdentifier = resource.instanceIdentifier
                        Log.d("tadada","came444")

                    }
                    is Resource.DataErrorInstance -> {
                        Log.d("tadada","came555")

                        // Handle error with instanceIdentifier
                        // For example:
                        // val errorCode = resource.errorCode
                        // val instanceIdentifier = resource.instanceIdentifier
                    }
                }
            }}) {
//            Log.d("checker","valll"+count)
            Text(text = "incrementThree${model.count.value}")
        }

    }
}

