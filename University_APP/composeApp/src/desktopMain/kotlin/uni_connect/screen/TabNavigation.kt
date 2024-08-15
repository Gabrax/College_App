package uni_connect.screen

import ContentWithMessageBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rememberMessageBarState
import uni_connect.Database.supabase

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab

    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(HomeScreen()) { navigator ->
            SlideTransition(navigator) }
    }
}

object Grades : Tab {
    private fun readResolve(): Any = Grades

    override val options: TabOptions
        @Composable
        get() {
            val title = "Grades"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(GradesScreen()) { navigator ->
            SlideTransition(navigator) }
    }
}

var isLoggedIn by mutableStateOf(true)

object Logout : Tab {
    private fun readResolve(): Any = Logout

    override val options: TabOptions
        @Composable
        get() {
            val title = "Logout"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }


    @Composable
    override fun Content() {
        val auth = remember { supabase.auth }
        val scope = rememberCoroutineScope()
        val messageBarState = rememberMessageBarState()
        ContentWithMessageBar(messageBarState = messageBarState){
            scope.launch {
                try {
                    auth.signOut()
                    messageBarState.addSuccess("Successfully logged-out")
                    delay(1500L)
                    val postLogoutSession = auth.currentSessionOrNull()
                    if(postLogoutSession == null){
                        isLoggedIn = false
                    }
                } catch (e: Exception) {
                    messageBarState.addError(Exception("Logout failed: ${e.message}"))
                }
            }
        }
    }
}