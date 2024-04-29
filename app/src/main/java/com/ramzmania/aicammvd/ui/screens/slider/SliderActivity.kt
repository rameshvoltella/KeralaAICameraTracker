/**
 * SliderActivity: An activity responsible for displaying a slider screen for onboarding.
 * Users can swipe through different slides introducing features of the application.
 * It also handles permission requests and navigation to the HomeActivity once the slider is passed.
 */
package com.ramzmania.aicammvd.ui.screens.slider

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.slider.SliderContentData
import com.ramzmania.aicammvd.ui.base.BaseComposeActivity
import com.ramzmania.aicammvd.ui.component.slider.HorizontalPagerWithLinesIndicatorScreen
import com.ramzmania.aicammvd.ui.screens.home.HomeActivity
import com.ramzmania.aicammvd.ui.theme.AiCameraApplicationTheme
import com.ramzmania.aicammvd.utils.Constants
import com.ramzmania.aicammvd.utils.PreferencesUtil
import com.ramzmania.aicammvd.viewmodel.MyViewModel
import com.ramzmania.aicammvd.viewmodel.slider.SliderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderActivity : BaseComposeActivity<SliderViewModel>() {
    private val NOTIFICATION_PERMISSION_CODE=1

    override fun getViewModelClass() = SliderViewModel::class.java

    override fun observeViewModel() {


    }

    override fun observeActivity() {
        // Check if slider screen has been passed previously
        if(PreferencesUtil.getString(applicationContext,Constants.SLIDER_SCREEN_TAG).equals(Constants.SLIDER_SCREEN_PASSED))
        {
            startActivity(Intent(applicationContext,HomeActivity::class.java))
            finish()

        }else {
            // Request notification permission if not granted
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            }
        }
//
//        val geofenceHelper = GeofenceHelper(this)
//        val cameraDataList = createCameraDataList()
//        geofenceHelper.addGeofences(cameraDataList)
    }

    override fun beforeOnContent() {
        // Wait for a short time before showing content and install splash screen
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
    // Generate data for slider content
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

    @Composable
    fun MyScreen(viewModel: MyViewModel) {
        Text(text = "Count: ${viewModel.count}")
        Button(onClick = { viewModel.incrementCount() }) {
            Text("Increment")
        }
    }

    @Preview
    @Composable
    fun PreviewMyScreen() {
        MyScreen(viewModel = MyViewModel())
    }
}