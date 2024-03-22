package com.ramzmania.aicammvd.ui.slider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.compose.slider.ComposeArticle
import com.ramzmania.aicammvd.ui.compose.slider.Greeting
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
                Column {
                    Greeting("Androidsherikkum")
                    ComposeArticle("Rajavu","dfbljwebfjbwejbfjhbefjhw","body2",R.drawable.ic_livevideo_doubt)
                }
                //Greeting("Androidsherikkum")
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Column {
            Greeting("Androidsherikkum")
            ComposeArticle("Rajavu","dfbljwebfjbwejbfjhbefjhw","body2", R.drawable.ic_livevideo_doubt)
        }
    }
}