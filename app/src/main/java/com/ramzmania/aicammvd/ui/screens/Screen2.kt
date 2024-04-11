package com.ramzmania.aicammvd.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ScreenTwo(count: Int, navigateTo: (route: String) -> Unit)
{
//    val testViewModel = viewModel<TestViewModel>()

//    val count by testViewModel.count.collectAsState()
    val model = viewModel<TestViewModel>()

    Column {
        Button(onClick = {}) {
//            Log.d("checker","valll"+count)
            Text(text = "increment${model.count.value}")
        }

    }
}

@Preview
@Composable
fun checkers()
{
//    ScreenTwo(null)
}