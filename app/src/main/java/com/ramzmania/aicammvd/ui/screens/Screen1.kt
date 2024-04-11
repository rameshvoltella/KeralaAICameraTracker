package com.ramzmania.aicammvd.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Screenone(
    viewModelStoreOwner: ViewModelStoreOwner,    navigateTo: (route: String) -> Unit

)
{
    val model = viewModel<TestViewModel>(viewModelStoreOwner = viewModelStoreOwner)

    Column {
        Button(onClick = {model.incrementCount(10000000)}) {
            Text(text = "increment")
        }
        Button( onClick = {navigateTo("scr2")}) {
            Text(text = "next"+model.count.value.toString())
        }
    }
}

@Preview
@Composable
fun checker()
{
//    Screenone()
}