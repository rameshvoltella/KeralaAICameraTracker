package com.ramzmania.aicammvd.viewmodel.slider

import androidx.lifecycle.viewModelScope
import com.ramzmania.aicammvd.data.ContextModule
import com.ramzmania.aicammvd.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SliderViewModel @Inject
constructor(componentContext: ContextModule
) : BaseViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
//            delay(3000)
            _isLoading.value = false
        }
    }
}