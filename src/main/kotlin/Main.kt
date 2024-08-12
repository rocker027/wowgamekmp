import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import main.MainScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "WowGame範本",
        state = WindowState(size = DpSize(1300.dp, 750.dp))
    ) {
        MainScreen()
    }
}
