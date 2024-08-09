package uni_connect

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import uni_connect.screen.HomeScreen

var exitAppFlag by mutableStateOf(false)

@Composable
@Preview
fun App(userViewModel: UserViewModel = UserViewModel()) {
    var showContent by remember { mutableStateOf(false) }
    val users by userViewModel.users

    MaterialTheme{
        Navigator(HomeScreen()) { navigator -> SlideTransition(navigator) }
    }

//    Box(modifier = Modifier.fillMaxSize()) {
//        MaterialTheme {
//            // Row with buttons on the top
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.DarkGray)
//                    .padding(horizontal = 0.10.dp, vertical = 23.dp), // Padding around the Row
//                horizontalArrangement = Arrangement.SpaceBetween, // Space between items
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Spacer to push Exit button to the right
//                Spacer(modifier = Modifier.weight(2f))
//
//                Button(
//                    onClick = { exitAppFlag = true },
//                    modifier = Modifier.padding(end = 13.dp).size(20.dp).offset(y = (-10).dp),
//                    shape = CircleShape,
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
//                ) {
//                    Text("X")
//                }
//            }
//
//            // Column for the main content
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(top = 64.dp) // Add padding to ensure it doesn't overlap with the Row
//                    .background(Color.DarkGray),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Button(onClick = {
//                    // Launch a coroutine to fetch data when the button is clicked
//                    userViewModel.viewModelScope.launch {
//                        userViewModel.fetchUsers()
//                        showContent = true
//                    }
//                }) {
//                    Text("Fetch Users")
//                }
//
//                AnimatedVisibility(visible = showContent) {
//                    Column(
//                        Modifier.fillMaxWidth(),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        users.forEach { user ->
//                            Text(
//                                text = "ID: ${user.id}, Username: ${user.username}, Email: ${user.email}",
//                                color = Color.White,
//                                modifier = Modifier.padding(8.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
}

