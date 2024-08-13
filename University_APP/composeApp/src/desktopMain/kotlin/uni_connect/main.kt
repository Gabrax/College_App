package uni_connect

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import uni_connect.Database.supabase
import university_connect.composeapp.generated.resources.Icon
import university_connect.composeapp.generated.resources.Res

const val pass = "Minecraftcher123@@"

const val WIDTH = 1000
const val HEIGHT = 700



fun main() = application {

    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(WIDTH.dp, HEIGHT.dp)
    )

//    val bitmap = useResource("Icon.png") { loadImageBitmap(it) }
//    val icon = BitmapPainter(bitmap)

    Window(
        state = windowState,
        onCloseRequest = {
            exitApplication()
            if (exitAppFlag) {
                exitApplication()
            }
        },
        resizable = true,
        title = "University Connect",
        icon = painterResource(Res.drawable.Icon),
        undecorated = false,
        alwaysOnTop = true,
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


