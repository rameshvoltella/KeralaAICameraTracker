package com.ramzmania.aicammvd.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.component.home.basicHomeLayer
import com.ramzmania.aicammvd.ui.theme.AiCameraApplicationTheme
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity:BaseComposeActivity<HomeViewModel>()
{
    override fun getViewModelClass()=HomeViewModel::class.java

    override fun observeViewModel() {
    }

    override fun observeActivity() {
    }

    override fun beforeOnContent() {
    }

    @Composable
    override fun setContent() {

        AiCameraApplicationTheme {
            Surface(modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow)) {

                basicHomeLayer()
            }
        }


    }


}