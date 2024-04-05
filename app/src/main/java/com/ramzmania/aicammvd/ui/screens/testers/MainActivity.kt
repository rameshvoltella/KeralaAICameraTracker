package com.ramzmania.aicammvd.ui.screens.testers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramzmania.aicammvd.ui.theme.AiCameraApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AiCameraApplicationTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val viewModel: MyViewModel = viewModel()
    MyScreen(viewModel)
}

@Composable
fun MyScreen(viewModel: MyViewModel) {
    val data by viewModel.count.collectAsState()
    val updateData: (String) -> Unit = viewModel::updateData

    MyScreenContent(data, updateData)
}

@Composable
fun MyScreenContent(data: String, onUpdateData: (String) -> Unit) {
    Column {
        Text(text = data)
        Button(onClick = { onUpdateData("New Value") }) {
            Text("Update Data")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AiCameraApplicationTheme {
        MyApp()
    }
}

