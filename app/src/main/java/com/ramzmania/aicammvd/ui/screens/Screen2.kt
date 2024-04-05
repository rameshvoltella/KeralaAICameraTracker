package com.ramzmania.aicammvd.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ScreenTwo(testViewModel: TestViewModel,navigateTo: (route: String) -> Unit)
{
    val count by testViewModel.count.collectAsState()

    Column {
        Button(onClick = {}) {
//            Log.d("checker","valll"+count)
            Text(text = "increment$count")
        }

    }
}

@Preview
@Composable
fun checkers()
{
//    ScreenTwo(null)
}