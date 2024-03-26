package com.ramzmania.aicammvd.ui.base
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseComposeActivity<ViewModel : BaseViewModel> : ComponentActivity() {

    protected lateinit var viewModel: ViewModel
    protected abstract fun getViewModelClass(): Class<ViewModel>

    var dialog: Dialog? = null

    abstract fun observeViewModel()
    abstract fun observeActivity()
    abstract fun setsplash()
//    abstract fun observeBeforeOnCreate()
    open fun observeActivityWithInstance(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("init", "initialized")
        setsplash()
        setContent {
            setContent()
        }
        initViewModel()

        observeViewModel()
        observeActivityWithInstance(savedInstanceState)
        observeActivity()
    }
    @Composable
    abstract fun setContent()

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[getViewModelClass()]
    }

    open fun hideProgressDialog() {
        try {
            dialog?.dismiss()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
