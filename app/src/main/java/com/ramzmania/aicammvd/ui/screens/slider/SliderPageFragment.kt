package com.ramzmania.aicammvd.ui.screens.slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.ramzmania.aicammvd.ui.screens.home.OnboardingUI
import com.ramzmania.aicammvd.ui.theme.MyApplicationTheme

class SliderPageFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                   OnboardingUI(
                        centerImage = "R.drawable.f",
                        imageDesc = "kona",
                        introtxt = "kona"
                    )

                }
            }
        }
    }

 /*   @Composable
    fun OnboardingUI(centerImage: String, imageDesc: String, introtxt: String) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().background(Color.Red)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CenterImage(image = centerImage, imageDesc = imageDesc)
                Text(text = introtxt)
            }
        }
    }*/
}
/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        OnboardingUI(centerImage = "R.drawable.f", imageDesc = "kona", introtxt = "kona")

    }


}*/