package com.ramzmania.aicammvd.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.theme.AiCameraApplicationTheme
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActitvity: BaseComposeActivity<TestViewModel>() {
    override fun getViewModelClass()=TestViewModel::class.java

    override fun observeViewModel() {
    }

    override fun observeActivity() {
    }

    override fun beforeOnContent() {
    }

    @Composable
    override fun setContent() {
        AiCameraApplicationTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
            ) {
                TestNavigationExample()
            }
        }
    }
}