package com.ramzmania.aicammvd.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Screenone( incrementCount: (Int) -> Unit,navigateTo: (route: String) -> Unit)
{
    Column {
        Button(onClick = {incrementCount(3000)}) {
            Text(text = "increment")
        }
        Button( onClick = {navigateTo("scr2")}) {
            Text(text = "next")
        }
    }
}

@Preview
@Composable
fun checker()
{
//    Screenone()
}