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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.jan.supabase.gotrue.auth
import rememberMessageBarState
import uni_connect.Database.currentUserProfile
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
                currentUserProfile.value.forEach{ user ->
                    Text(
                        text = "${user.name} ${user.surname}",
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Button(
                    onClick = {
                        navigator.push(DetailsScreen())
                    }
                ) {
                    Text(text = "Check Details")
                }

                Spacer(modifier = Modifier.height(16.dp))

                //DEBUG
//                val currentUser = auth.currentUserOrNull()
//                println(currentUser?.id)


            }

        }
    }
}