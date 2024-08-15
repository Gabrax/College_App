package uni_connect.screen

import ContentWithMessageBar
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rememberMessageBarState
import uni_connect.Database.fetchCurrentUsername
import uni_connect.Database.insertDisplayName
import uni_connect.Database.supabase


class NameSurname: Screen{

    @Composable
    override fun Content() {
        val messageBarState = rememberMessageBarState()
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()

        val auth = remember { supabase.auth }
        var userName by remember { mutableStateOf("") }
        var userSurname by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf<String?>(null) }
        var surnameError by remember { mutableStateOf<String?>(null) }


        ContentWithMessageBar(messageBarState = messageBarState){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Enter your\ndisplay name:",
                    modifier = Modifier.wrapContentSize(Alignment.Center),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )

                Spacer(modifier = Modifier.height(25.dp))

                OutlinedTextField(
                    value = userName,
                    onValueChange = { newName ->
                        val filtered = newName.filter { it != ' ' && it != '\n' }
                        userName = filtered
                        nameError = if (!newName.all { it.isLetter() }) {
                            "Only can contain alphabetic characters"
                        } else {
                            null
                        }
                    },
                    label = { Text("Name") },
                    isError = nameError != null,
                    supportingText = {
                        if (nameError != null) {
                            Text(
                                text = nameError!!,
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                )

                Spacer(modifier = Modifier.height(12.dp))


                OutlinedTextField(
                    value = userSurname,
                    onValueChange = { newSurname ->
                        val filtered = newSurname.filter { it != ' ' && it != '\n' }
                        userSurname = filtered
                        surnameError = if (!newSurname.all { it.isLetter() }) {
                            "Only can contain alphabetic characters"
                        } else {
                            null
                        }
                    },
                    label = { Text("Surname") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = surnameError != null,
                    supportingText = {
                        if (surnameError != null) {
                            Text(
                                text = surnameError!!,
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            try {
                                // Check if the user is logged in after sign-up
                                val currentSession = auth.currentSessionOrNull()
                                if (currentSession != null) {
                                    insertDisplayName(userName,userSurname)
                                    messageBarState.addSuccess("Successfully added display name")
                                    delay(1500L)
                                    fetchCurrentUsername()
                                    navigator.replace(MainScreen())
                                }
                            } catch (e: Exception) {
                                messageBarState.addError(Exception("Error appending info"))
                            }
                        }
                    }
                ){
                    Text(text = "Configure")
                }
            }
        }
    }
}