package com.ramzmania.aicammvd.ui.screens.home

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.component.slider.HorizontalPagerWithLinesIndicatorScreen
import com.ramzmania.aicammvd.ui.theme.MyApplicationTheme
import com.ramzmania.aicammvd.viewmodel.slider.SliderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : BaseComposeActivity<SliderViewModel>() {

    override fun getViewModelClass() = SliderViewModel::class.java

    override fun observeViewModel() {

    }
    override fun observeActivity() {
    }

    override fun setsplash() {

        Thread.sleep(3000)
        installSplashScreen()
    }


    @Composable
    override fun setContent() {
        MyApplicationTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
//                    Greeting("Android")
                OnboardingUI(centerImage = "R.drawable.f", imageDesc = "kona", introtxt = "kona")

            }
        }
    }
  /*  override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(3000)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    OnboardingUI(centerImage = "R.drawable.f", imageDesc = "kona", introtxt = "kona")

                }
            }
        }
        // Set up an OnPreDrawListener to the root view.


    }*/
}

@Composable
fun OnboardingUI(centerImage: String, imageDesc: String, introtxt: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(Color.Green)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
           // CenterImage(image = centerImage, imageDesc = imageDesc)
            Text(text = introtxt)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        OnboardingUI(centerImage = "R.drawable.f", imageDesc = "kona", introtxt = "kona")

    }


}