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
import uni_connect.Database.supabase
import java.io.File

const val pass = "Minecraftcher123@@"

const val WIDTH = 1000
const val HEIGHT = 700

fun main() = application {

    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(WIDTH.dp, HEIGHT.dp)
    )

    val iconBitmap: ImageBitmap = loadImageBitmap(File("src/desktopMain/Images/Icon.png").inputStream())
    val icon = BitmapPainter(iconBitmap)

    Window(
        state = windowState,
        onCloseRequest = {
            exitApplication()
            if (exitAppFlag) {
                exitApplication()
            }
        },
        resizable = false,
        title = "University Connect",
        icon = icon,
        undecorated = false,
        alwaysOnTop = true
    ) {
        App()
    }

    println(supabase)

    LaunchedEffect(exitAppFlag) {
        if (exitAppFlag) {
            exitApplication()
        }
    }

}


