package com.ramzmania.aicammvd.ui.slider

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.home.Greeting
import com.ramzmania.aicammvd.ui.theme.MyApplicationTheme
import com.ramzmania.aicammvd.viewmodel.slider.SliderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderActivity : BaseComposeActivity<SliderViewModel>() {
    override fun getViewModelClass()=SliderViewModel::class.java

    override fun observeViewModel() {
    }

    override fun observeActivity() {
    }

    @Composable
    override fun setContent() {
        MyApplicationTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background // Updated MaterialTheme.colorScheme to MaterialTheme.colors
            ) {
                Greeting("Androidolakka")
            }
        }
    }
}