package com.ramzmania.aicammvd.viewmodel

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _count = mutableStateOf(0)
    val count: Int = _count.value

    fun incrementCount() {
        _count.value++
    }
}

