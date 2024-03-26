package com.ramzmania.aicammvd.ui.screens.slider

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.dto.slider.SliderContentData
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.component.slider.HorizontalPagerWithLinesIndicatorScreen
import com.ramzmania.aicammvd.ui.component.slider.ViewPagerLinesIndicator
import com.ramzmania.aicammvd.ui.theme.MyApplicationTheme
import com.ramzmania.aicammvd.viewmodel.slider.SliderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderActivity : BaseComposeActivity<SliderViewModel>() {
    override fun getViewModelClass() = SliderViewModel::class.java

    override fun observeViewModel() {
        installSplashScreen()

    }
    override fun observeActivity() {
    }

    @Composable
    override fun setContent() {
        val dataList = generateSliderContentData()
        MyApplicationTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background // Updated MaterialTheme.colorScheme to MaterialTheme.colors
            ) {
                HorizontalPagerWithLinesIndicatorScreen(dataList = dataList)

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
                R.drawable.came_two
            )
        )


        return dataList
    }

}