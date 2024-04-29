package com.ramzmania.aicammvd.viewmodel

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
/**
 * This is a test model i created while playing with compose just ignore it i don't wanna delete :)*/
class MyViewModel : ViewModel() {
    private val _count = mutableStateOf(0)
    val count: Int = _count.value

    fun incrementCount() {
        _count.value++
    }
}

