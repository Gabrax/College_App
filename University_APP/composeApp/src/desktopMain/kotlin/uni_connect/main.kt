package uni_connect

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.File

const val pass = "LI8mAEY3RMotM36K"

const val WIDTH = 1000
const val HEIGHT = 700

fun main() = application {

    val userViewModel = UserViewModel()

    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(WIDTH.dp, HEIGHT.dp)
    )

    val iconBitmap: ImageBitmap = loadImageBitmap(File("src/desktopMain/Images/Icon.png").inputStream())
    val icon = BitmapPainter(iconBitmap)

    Window(
        state = windowState,
        onCloseRequest = {
            if (exitAppFlag) {
                exitApplication() // Call exitApplication if the flag is true
            }
        },
        resizable = false,
        title = "University Connect",
        icon = icon,
        undecorated = true,
        alwaysOnTop = true
    ) {
        App(userViewModel)
    }


    println(supabase)

    LaunchedEffect(exitAppFlag) {
        if (exitAppFlag) {
            exitApplication() // Ensure the application exits when the flag is true
        }
    }

}


