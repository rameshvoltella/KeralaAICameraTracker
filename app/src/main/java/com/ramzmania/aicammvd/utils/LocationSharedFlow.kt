package com.ramzmania.aicammvd.utils

import android.location.Location
import kotlinx.coroutines.flow.MutableSharedFlow

object LocationSharedFlow {
    val locationFlow = MutableSharedFlow<Location>(replay = 1, extraBufferCapacity = 10)
//    val locationFlow = MutableSharedFlow<Pair<Double, Double>>(replay = 1, extraBufferCapacity = 10)
    val serviceStatus = MutableSharedFlow<Boolean>(replay = 1, extraBufferCapacity = 10)

}