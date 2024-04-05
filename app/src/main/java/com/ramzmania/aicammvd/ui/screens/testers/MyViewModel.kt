package com.ramzmania.aicammvd.ui.screens.testers

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyViewModel : ViewModel() {
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
}