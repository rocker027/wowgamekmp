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
fun MainScreen(viewModel: MainViewModel = MainViewModel()) {

    val titleState by viewModel.titleState.collectAsState()
    val environmentState by viewModel.environmentState.collectAsState()

    MaterialTheme {
        Column {
            InputTextView(
                title = "標題",
                value = titleState,
                hint = "請輸入標題",
                onValueChange = { viewModel.updateTitle(it) },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            )

            InputTextView(
                title = "測試環境",
                value = environmentState,
                hint = "請輸入測試環境",
                onValueChange = { viewModel.updateEnvironment(it) },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            )
        }
    }
}

@Composable
fun InputTextView(
    title: String, value: String, hint: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    TextField(modifier = modifier,
        value = value,
        placeholder = { Text(hint) },
        onValueChange = { onValueChange(it) },
        label = { Text(title) })
}