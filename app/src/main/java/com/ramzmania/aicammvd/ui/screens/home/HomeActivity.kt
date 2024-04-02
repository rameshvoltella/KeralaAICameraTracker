package com.ramzmania.aicammvd.ui.screens.home

import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.component.home.BasicHomeLayer
import com.ramzmania.aicammvd.ui.theme.AiCameraApplicationTheme
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseComposeActivity<HomeViewModel>() {
    override fun getViewModelClass() = HomeViewModel::class.java

    override fun observeViewModel() {
    }

    override fun observeActivity() {
    }

    override fun beforeOnContent() {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))

    }

    @Composable
    override fun setContent() {

        AiCameraApplicationTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
            ) {

                BasicHomeLayer()
            }
        }


    }


}