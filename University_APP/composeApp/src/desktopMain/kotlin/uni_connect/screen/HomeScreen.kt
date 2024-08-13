package uni_connect.screen

import ContentWithMessageBar
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rememberMessageBarState
import uni_connect.Database.supabase

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val auth = remember { supabase.auth }
        val messageBarState = rememberMessageBarState()

        ContentWithMessageBar(messageBarState = messageBarState){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Button(
                    onClick = {
                        navigator.push(DetailsScreen())
                    }
                ) {
                    Text(text = "Check Details")
                }

                Spacer(modifier = Modifier.height(16.dp))

                //DEBUG
//                val currentSession = auth.currentSessionOrNull()
//                println(currentSession)

                Button(
                    onClick = {
                        scope.launch {
                            try {
                                auth.signOut()
                                //DEBUG
                                val postLogoutSession = auth.currentSessionOrNull()
                                println("Session after sign out: $postLogoutSession")
                                messageBarState.addSuccess("Successfully logged-out")
                                delay(1500L)
                                navigator.replace(LoginScreen())
                            } catch (e: Exception) {
                                messageBarState.addError(Exception("Logout failed: ${e.message}"))
                            }
                        }
                    }
                ) {
                    Text(text = "Log Out")
                }
            }

        }
    }
}