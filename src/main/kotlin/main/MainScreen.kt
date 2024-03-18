package main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
@Preview
fun MainScreen(viewModel: MainViewModel = MainViewModel()) {

    val titleState by viewModel.titleState.collectAsState()
    val environmentState by viewModel.environmentState.collectAsState()
    val tagsState by viewModel.tagsState.collectAsState()
    var newTagState by remember { mutableStateOf("") }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            InputTextView(
                title = "標題",
                value = titleState,
                hint = "請輸入標題",
                onValueChange = { viewModel.updateTitle(it) },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                Column {
                    InputTextView(
                        title = "測試環境",
                        value = environmentState,
                        hint = "請輸入測試環境",
                        onValueChange = { viewModel.updateEnvironment(it) },
                        modifier = Modifier.padding(16.dp).size(300.dp, 50.dp)
                    )

                    TagView(
                        tags = tagsState,
                        onNewTagValueText = newTagState,
                        onNewTagValueChange = { newTagState = it },
                        onNewTagClick = {
                            viewModel.addTag(newTagState)
                            newTagState = ""
                        },
                        onCheckedChange = { viewModel.onCheckedChange(it) },
                        modifier = Modifier.padding(16.dp).size(300.dp, 50.dp)
                    )
                }

                Column(
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 16.dp).weight(1f)
                ) {
                    Text(
                        text = "預覽",
                        modifier = Modifier
                    )
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(color = Color.LightGray, shape = MaterialTheme.shapes.medium)
                    ) {
                        Text(
                            text = viewModel.composeResultFlow.collectAsState("").value,
                            modifier = Modifier.fillMaxSize()
                                .padding(8.dp)
                        )
                    }

                }
            }

            Button(
                onClick = { viewModel.onSentClick() },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text("送出")
            }
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

@Composable
fun TagView(
    tags: List<TagItem>,
    onNewTagValueText: String,
    onNewTagValueChange: (String) -> Unit,
    onNewTagClick: () -> Unit,
    onCheckedChange: (TagItem) -> Unit, modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier.size(300.dp)
        ) {
            InputTextView(
                title = "新增標籤",
                value = onNewTagValueText,
                hint = "請輸入標籤",
                onValueChange = onNewTagValueChange,
                modifier = Modifier.size(200.dp)
            )
            Button(
                onClick = { onNewTagClick.invoke() },
                modifier = Modifier.padding(start = 4.dp).size(50.dp)
            ) {
                Text("新增")
            }
        }
        tags.forEach { tag ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = tag.isSelected, onCheckedChange = { onCheckedChange(tag) })
                Text(text = tag.name, modifier = Modifier)
            }
        }
    }
}