package com.ramzmania.aicammvd.ui.compose.slider

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!", fontSize = 24.sp)
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Greeting(name = "Android")
    }
