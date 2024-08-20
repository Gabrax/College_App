package uni_connect.screen

import ContentWithMessageBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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

        val nameFocusRequester = remember { FocusRequester() }
        val surnameFocusRequester = remember { FocusRequester() }
        val buttonFocusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        val specialCharactersToFilter = setOf(
            ' ', '\n', '\t', '(', ')',
            '=', '+', '[', ']', '{', '}', ';', ':', '"',
            '\'', ',', '<', '>', '?', '/'
        )

        val configureClick = {
            scope.launch {
                try {
                    val currentSession = auth.currentSessionOrNull()
                    if (currentSession != null) {
                        messageBarState.addSuccess("Successfully logged in")
                        delay(1500L)
                        navigator.replace(MainScreen())
                        fetchCurrentUsername()
                    }
                } catch (e: Exception) {
                        messageBarState.addError(Exception("Invalid Credentials"))
                }
            }
        }

        ContentWithMessageBar(messageBarState = messageBarState){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text("Enter your\ndisplay name:",
                    modifier = Modifier.wrapContentSize(Alignment.Center),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )

                Spacer(modifier = Modifier.height(25.dp))

                LaunchedEffect(Unit) {
                    nameFocusRequester.requestFocus()
                }
                //!newName.all { it.isLetter() }

                OutlinedTextField(
                    value = userName,
                    onValueChange = { newName ->
                        val filtered = newName.filter { it !in specialCharactersToFilter }
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
                    modifier = Modifier
                        .focusRequester(nameFocusRequester)
                        .onKeyEvent { event ->
                            if (event.key == Key.Tab) {
                                surnameFocusRequester.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { surnameFocusRequester.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = userSurname,
                    onValueChange = { newSurname ->
                        val filtered = newSurname.filter { it !in specialCharactersToFilter }
                        userSurname = filtered
                        surnameError = if (!newSurname.all { it.isLetter() }) {
                            "Only can contain alphabetic characters"
                        } else {
                            null
                        }
                    },
                    label = { Text("Surname") },
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
                    modifier = Modifier
                        .focusRequester(surnameFocusRequester)
                        .onKeyEvent { event ->
                            if (event.key == Key.Tab) {
                                focusManager.moveFocus(FocusDirection.Next)
                                true
                            } else {
                                false
                            }
                        },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { configureClick() },
                            modifier = Modifier
                                .focusRequester(buttonFocusRequester)
                                .onKeyEvent { event ->
                            if (event.key == Key.Enter && userName.isNotEmpty() && userSurname.isNotEmpty()) {
                                configureClick() // Trigger the button's onClick action
                                true // Event is consumed
                            } else {
                                false // Event is not consumed
                            }
                        },
                    enabled = userName.isNotEmpty() && userSurname.isNotEmpty()
                ){
                    Text(text = "Configure")
                }
            }
        }
    }
}