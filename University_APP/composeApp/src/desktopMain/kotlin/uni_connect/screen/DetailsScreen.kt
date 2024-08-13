package uni_connect.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import uni_connect.Database.fetchUsers
import uni_connect.Database.users

class DetailsScreen : Screen {

    // Show content flag
    var showContent by mutableStateOf(false)

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    fetchUsers()
                    showContent = true
                }
            }) {
                Text("Fetch Users")
            }

            AnimatedVisibility(visible = showContent) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    users.value.forEach { user ->
                        Text(
                            text = "ID: ${user.id}, Username: ${user.username}, Email: ${user.email}",
                            color = Color.Black,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            // Back button to pop the navigator stack
            Button(onClick = { navigator.pop() }) {
                Text(text = "Go Back")
            }
        }
    }
}