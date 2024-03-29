package com.ramzmania.aicammvd.ui.screens.slider

import android.app.Activity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.dto.slider.SliderContentData
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.component.slider.HorizontalPagerWithLinesIndicatorScreen
import com.ramzmania.aicammvd.ui.theme.AiCameraApplicationTheme
import com.ramzmania.aicammvd.viewmodel.slider.SliderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderActivity : BaseComposeActivity<SliderViewModel>() {
    override fun getViewModelClass() = SliderViewModel::class.java

    override fun observeViewModel() {


    }

    override fun observeActivity() {
    }

    override fun beforeOnContent() {
        Thread.sleep(3000)
        installSplashScreen()
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))

    }


    @Composable
    override fun setContent() {
        val dataList = generateSliderContentData()
        val navController = rememberNavController()
        AiCameraApplicationTheme {
           // setStatusBarColorColor(Color.Green)
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background // Updated MaterialTheme.colorScheme to MaterialTheme.colors
            ) {
                HorizontalPagerWithLinesIndicatorScreen(dataList = dataList,this)

            }
        }
    }


    /*   @Preview(showBackground = true)
       @Composable
       fun GreetingPreview() {
           Column {
               OnboardingUI(centerImage = "R.drawable.f", imageDesc = "kona", introtxt = "kona")

   //            Greeting("Androidsherikkum")
   //            ComposeArticle("Rajavu","dfbljwebfjbwejbfjhbefjhw","body2", R.drawable.ic_livevideo_doubt)
           }
       }*/

    /* @Composable
     fun OnboardingUI(centerImage: String, imageDesc: String, introtxt: String) {
         Column(
             modifier = Modifier
                 .fillMaxSize()
                 .background(Color.Red)
         ) {
             Box(contentAlignment = Alignment.Center) {
                 CenterImage(image = centerImage, imageDesc = imageDesc)
                 Text(text = introtxt)
             }
         }
     }*/

    private fun generateSliderContentData(): List<SliderContentData> {
        val dataList = mutableListOf<SliderContentData>()
        dataList.add(
            SliderContentData(
                "AIWatch",
                "Empowering Safe Driving with AI Camera Detection",
                R.drawable.cam_one
            )
        )
        dataList.add(
            SliderContentData(
                "DriveGuard",
                "Stay Alert, Drive Safe: AI Camera Detection at Your Service",
                R.drawable.cam_location
            )
        )
        dataList.add(
            SliderContentData(
                "SmartLens",
                "AI Eyes on the Road: Warning Drivers Before the Camera Zone",
                R.drawable.came_new
            )
        )


        return dataList
    }

    @Composable
    fun setStatusBarColorColor(color: Color) {

        val view = LocalView.current
        if (!view.isInEditMode) {
            LaunchedEffect(key1 = true)
            {
                val window = (view.context as Activity).window
                window.statusBarColor = color.toArgb()
            }

        }
    }
}