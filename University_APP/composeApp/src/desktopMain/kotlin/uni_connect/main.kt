package uni_connect

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

const val pass = "LI8mAEY3RMotM36K"

const val WIDTH = 1000
const val HEIGHT = 700

fun main() = application {

    val userViewModel = UserViewModel()

    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(WIDTH.dp, HEIGHT.dp),
    )

    Window(
        state = windowState,
        onCloseRequest = ::exitApplication,
        resizable = false,
        title = "University Connect",
    ) {
        App(userViewModel)
    }


    println(supabase)

//    runBlocking {
//        val result = testConnection()
//        println(result) // Output the result of the connection test
//    }

}
