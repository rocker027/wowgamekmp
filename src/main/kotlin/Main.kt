import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import main.MainPage

fun main() = application {
    Window(onCloseRequest = ::exitApplication,title = "WowGame範本") {
        MainPage()
    }
}
