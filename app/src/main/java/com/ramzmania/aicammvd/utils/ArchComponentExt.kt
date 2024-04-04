package com.ramzmania.aicammvd.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ramzmania.aicammvd.events.SingleEvent

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.observe(refrence:LifecycleOwner,liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(refrence, Observer { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.observeEvent(liveData: LiveData<SingleEvent<T>>, action: (t: SingleEvent<T>) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

/**
 * Observe a LiveData within a Composable. This function is an extension function on LiveData.
 */

@Composable
fun <T> LiveData<T>.observe(owner: LifecycleOwner, onChanged: (T) -> Unit) {
    DisposableEffect(this, owner) {
        val observer = Observer<T> { value ->
            onChanged(value)
        }

        observe(owner, observer)

        onDispose {
            removeObserver(observer)
        }
    }
}

@Composable
fun <T> LiveData<T>.observeAsStateInComposable(owner: LifecycleOwner): State<T?> {
    val result = mutableStateOf<T?>(null)

    DisposableEffect(this, owner) {
        val observer = Observer<T> { value ->
            result.value = value
        }

        observe(owner, observer)

        onDispose {
            removeObserver(observer)
        }
    }

    return result
}

@Composable
fun <T> LiveData<SingleEvent<T>>.observeEvent(owner: LifecycleOwner, onChanged: (SingleEvent<T>) -> Unit) {
    DisposableEffect(this, owner) {
        val observer = Observer<SingleEvent<T>> { event ->
            event?.let(onChanged)
        }

        observe(owner, observer)

        onDispose {
            removeObserver(observer)
        }
    }
}