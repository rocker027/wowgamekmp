package main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
@Preview
fun MainPage(viewModel: MainViewModel = MainViewModel()) {

    var textTitle by remember { mutableStateOf("") }
    var textEnvironment by remember { mutableStateOf("") }

    MaterialTheme {
        Column {
            TextField(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                value = textTitle,
                placeholder = { Text("請輸入標題") },
                onValueChange = {
                    textTitle = it
                },
                label = { Text("標題") }
            )

            TextField(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                value = textEnvironment,
                placeholder = { Text("請輸入測試環境") },
                onValueChange = {
                    textEnvironment = it
                },
                label = { Text("測試環境") }
            )
        }
    }
}