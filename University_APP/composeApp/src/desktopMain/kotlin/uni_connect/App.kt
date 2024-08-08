package uni_connect

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(userViewModel: UserViewModel = UserViewModel()) {
    var showContent by remember { mutableStateOf(false) }
    val users by userViewModel.users

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                // Launch a coroutine to fetch data when the button is clicked
                userViewModel.viewModelScope.launch {
                    userViewModel.fetchUsers()
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
                    users.forEach { user ->
                        Text(
                            text = "ID: ${user.id}, Username: ${user.username}, Email: ${user.email}",
                            color = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

