package com.ramzmania.aicammvd.ui.screens

import android.util.Log
import com.ramzmania.aicammvd.data.local.LocalRepository
import com.ramzmania.aicammvd.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class TestViewModel @Inject constructor(val localRepository: LocalRepository) :BaseViewModel(){
    private val _count = MutableStateFlow(0)
    //    val count: Int = _count.value
    val count =_count.asStateFlow()

    fun incrementCount(value:Int) {
        _count.value=value
       // Log.d("changed","value"+_count)
    }


}