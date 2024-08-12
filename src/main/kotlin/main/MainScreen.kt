package main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import kotlinx.coroutines.launch


@Composable
fun MainScreen(viewModel: MainViewModel = MainViewModel()) {

    val titleState by viewModel.titleState.collectAsState()
    val environmentState by viewModel.environmentState.collectAsState()
    val tagsState by viewModel.tagsState.collectAsState()
    var newTagState by remember { mutableStateOf("") }
    val alertDialog = rememberSaveable { mutableStateOf(false) }
    val rememberDialogState = rememberDialogState(size = DpSize(300.dp, 200.dp))
    val scope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }


    MaterialTheme {
            // ...
//            Button(
//                onClick = {
//                    scope.launch {
//                        showDialog.value = true
//                    }
//                },
//                modifier = Modifier.padding(16.dp).fillMaxWidth()
//            ) {
//                Text("送出")
//            }

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("確認送出") },
                    text = { Text("你確定要送出嗎？") },
                    confirmButton = {
                        Button(onClick = { /* 確認按鈕的處理程式碼 */ }) {
                            Text("確認")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog.value = false }) {
                            Text("取消")
                        }
                    }
                )
        }


        Column(modifier = Modifier.fillMaxSize()) {
            InputTextView(
                title = "標題",
                value = titleState,
                hint = "請輸入標題",
                onValueChange = { viewModel.updateTitle(it) },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Row(modifier = Modifier.wrapContentSize().padding(start = 16.dp, top = 16.dp)) {
                        InputTextView(
                            title = "測試環境",
                            value = environmentState,
                            hint = "請輸入測試環境",
                            onValueChange = { viewModel.updateEnvironment(it) },
                        )

                        Button(
                            onClick = {
                                scope.launch {

                                }

                            },
                            modifier = Modifier.padding(start = 4.dp).wrapContentSize()
                        ) {
                            Text(">")
                        }
                    }


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
                onClick = {
                    viewModel.copyText()
                },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text("複製文字")
            }
        }
    }
}


@Composable
fun showMoreDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        // ...
    }
}

@Composable
@Preview
fun showMoreDialog(onDismiss: () -> Unit, alertDialog: MutableState<Boolean>) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("確定送出?")
            Button(
                onClick = {

                },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text("確定")
            }
        }
    }
}

@Composable
@Preview
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
@Preview
fun TagView(
    tags: List<TagItem>,
    onNewTagValueText: String,
    onNewTagValueChange: (String) -> Unit,
    onNewTagClick: () -> Unit,
    onCheckedChange: (TagItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    ) {
        Row() {
            InputTextView(
                title = "新增標籤",
                value = onNewTagValueText,
                hint = "請輸入標籤",
                onValueChange = onNewTagValueChange,
                modifier = Modifier
            )
            Button(
                onClick = { onNewTagClick.invoke() },
                modifier = Modifier.padding(start = 4.dp).wrapContentSize()
            ) {
                Text("新增")
            }
        }
        LazyColumn() {
            items(items = tags) { tag ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(checked = tag.isSelected, onCheckedChange = { onCheckedChange(tag) })
                    Text(text = tag.name, modifier = Modifier.clickable {
                        onCheckedChange(tag)
                    })
                }
            }
        }
    }
}