package main

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class MainViewModel {
    val titleState = MutableStateFlow("")
    val environmentState = MutableStateFlow("")
    val tagsState = MutableStateFlow<List<TagItem>>(
        listOf(
            TagItem("Android", false),
            TagItem("iOS", false),
            TagItem("Web", false),
            TagItem("Backend", false),
            TagItem("Frontend", false),
        )
    )

    val environmentItems = listOf(
        EnvironmentItem("Beta環境", false),
        EnvironmentItem("Demo環境", false),
        EnvironmentItem("Alpho環境", false),
        EnvironmentItem("測試環境4", false),
        EnvironmentItem("測試環境5", false),
    )


    val composeResultFlow: Flow<String> = combine(
        titleState,
        environmentState,
        tagsState
    ) { title, environment, tags ->
        buildString {
            tags.filter { it.isSelected }.forEach {
                append("[")
                append(it.name)
                append("]")
            }
            append("$title\n")
            append("\n")
            append("測試環境：$environment\n")

        }
    }

    fun updateTitle(title: String) {
        titleState.value = title
    }

    fun updateEnvironment(environment: String) {
        environmentState.value = environment
    }

    fun updateTags(tags: List<TagItem>) {
        tagsState.value = tags
    }

    fun onCheckedChange(tag: TagItem) {
        val tags = tagsState.value.toMutableList()
        val index = tags.indexOf(tag)
        tags[index] = tag.copy(isSelected = !tag.isSelected)
        tagsState.value = tags
    }

    fun addTag(newTagState: String) {
        val tags = tagsState.value.toMutableList()
        tags.add(TagItem(newTagState, true))
        tagsState.value = tags
    }

    fun onSentClick() {
        println("${getSelectTagsText()}${titleState.value}")
    }

    private fun getSelectTagsText(): String = buildString {
        tagsState.value.filter { it.isSelected }.forEach {
            append("[")
            append(it.name)
            append("]")
        }
    }

}

data class EnvironmentItem(
    val name: String,
    val isSelected: Boolean,
    val listSteps: List<String> = listOf(
        "於網址列輸入網址：18.142.166.177",
        "Step 2",
        "Step 3",
        "Step 4",
        "Step 5",
    )
)

data class TestStepItem(
    val name: String,
    val isSelected: Boolean,
    val listSteps: List<String> = listOf(
        "進入wow大廳",
        "進入愛麗絲夢遊仙境遊戲，正常執行一局且進入免費遊戲",
        "免費遊戲20局中，在第7局又再獲得10局免費遊戲",
        "確認前台與細單展示是否正確"
    )
)

data class TagItem(
    val name: String,
    val isSelected: Boolean
)