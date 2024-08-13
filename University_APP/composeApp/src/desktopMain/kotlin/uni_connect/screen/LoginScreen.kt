package uni_connect.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch
import uni_connect.Database.signInWithEmail
import uni_connect.Database.signUpNewUserWithEmail
import uni_connect.Database.supabase

class LoginScreen: Screen {


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val scope = rememberCoroutineScope()
        val auth = remember { supabase.auth }
        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }



        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = userEmail,
                onValueChange = { userEmail = it },
                placeholder = { Text("Enter your email") },
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),

            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            signUpNewUserWithEmail(userEmail, userPassword)
                        } catch (e: Exception) {
                            signInWithEmail(userEmail, userPassword)
                        }
                    }
                }
            ){
                Text(text = "sign in")
            }

            Button(
                onClick = {
                    navigator.push(
                        DetailsScreen()
                    )
                }
            ){
                Text(text = "check details")
            }
        }
    }
}