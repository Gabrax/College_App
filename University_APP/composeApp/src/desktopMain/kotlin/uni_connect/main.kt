package uni_connect

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import uni_connect.Database.supabase
import university_connect.composeapp.generated.resources.Res
import university_connect.composeapp.generated.resources.Unilogo


const val WIDTH = 1000
const val HEIGHT = 700

fun main() = application {

//    techNews()
//    scienceNews()
//    healthNews()
//    sportsNews()
//    businessNews()
//    entertainmentNews()

    val auth = remember { supabase.auth }

    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(WIDTH.dp, HEIGHT.dp)
    )

    val scope = MainScope()

    Window(
        state = windowState,
        onCloseRequest = {
            scope.launch {
                val currSession = auth.currentSessionOrNull()
                if(currSession != null) {
                    auth.signOut()
                    println("logging out")
                    exitApplication()
                }else exitApplication()

                if (exitAppFlag) {
                    exitApplication()
                }
            }
        },
        resizable = false,
        title = "University Connect",
        icon = painterResource(Res.drawable.Unilogo),
        undecorated = false,
        alwaysOnTop = false,

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


