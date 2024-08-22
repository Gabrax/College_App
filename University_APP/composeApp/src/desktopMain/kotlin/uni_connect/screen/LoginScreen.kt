package uni_connect.screen

import ContentWithMessageBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import rememberMessageBarState
import uni_connect.Database.*
import university_connect.composeapp.generated.resources.Res
import university_connect.composeapp.generated.resources.Unilogo
import university_connect.composeapp.generated.resources.Unititle

class LoginScreen: Screen {


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val messageBarState = rememberMessageBarState()

        val scope = rememberCoroutineScope()
        val auth = remember { supabase.auth }
        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }
        var passError by remember { mutableStateOf<String?>(null) }
        var emailError by remember { mutableStateOf<String?>(null) }

        val emailFocusRequester = remember { FocusRequester() }
        val passwordFocusRequester = remember { FocusRequester() }
        val buttonFocusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        val specialCharactersToFilter = setOf(
            ' ', '\n', '\t', '(', ')',
            '=', '+', '[', ']', '{', '}', ';', ':', '"',
            '\'', ',', '<', '>', '?', '/'
        )

        val onSignInClick = {
            scope.launch {
                try {
                    signUpNewUserWithEmail(userEmail, userPassword)
                    val currentSession = auth.currentSessionOrNull()
                    if (currentSession != null) {
                        messageBarState.addSuccess("Successfully created account")
                        delay(1500L)
                        navigator.replace(NameSurname())
                    }
                } catch (e: Exception) {
                    try {
                        signInWithEmail(userEmail, userPassword)
                        val currentSession = auth.currentUserOrNull()
                        if (currentSession != null) {
                            messageBarState.addSuccess("Successfully logged in")
                            delay(1500L)
                            fetchCurrentUsername()
                            currentSession.email?.let { fetchCurrUser1YGrades(it) }
                            fetchCurrUserImage()
                            navigator.replace(MainScreen())
                        }
                    } catch (e: Exception) {
                        println(e.message)
                        messageBarState.addError(Exception("Invalid Credentials"))
                    }
                }
            }
        }

        ContentWithMessageBar(messageBarState = messageBarState){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Image(painter = painterResource(Res.drawable.Unilogo), modifier = Modifier.size(150.dp), contentDescription = "")
                Image(painter = painterResource(Res.drawable.Unititle),modifier = Modifier.width(300.dp).height(50.dp), contentDescription = "")

                Spacer(modifier = Modifier.height(10.dp))

                LaunchedEffect(Unit) {
                    emailFocusRequester.requestFocus()
                }

                OutlinedTextField(
                    value = userEmail,
                    onValueChange = { newEmail ->
                        val filtered = newEmail.filter { it !in specialCharactersToFilter }
                        userEmail = filtered
                        emailError = if (newEmail.length < 4 && newEmail.isNotEmpty()) {
                            "Incorrect Email"
                        } else {
                            null
                        }
                    },
                    label = { Text("Email") },
                    leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = "Email") },
                    isError = emailError != null,
                    supportingText = {
                        if (emailError != null) {
                            Text(
                                text = emailError!!,
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    modifier = Modifier
                        .focusRequester(emailFocusRequester)
                        .onKeyEvent { event ->
                            if (event.key == Key.Tab) {
                                passwordFocusRequester.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocusRequester.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = userPassword,
                    onValueChange = { newPassword ->
                        val filteredPassword = newPassword.filter { it !in specialCharactersToFilter }
                        userPassword = filteredPassword
                        passError = if (newPassword.length < 6 && newPassword.isNotEmpty()) {
                            "Min.length is 6 characters"
                        } else {
                            null
                        }
                    },
                    label = { Text("Password") },
                    leadingIcon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passError != null,
                    supportingText = {
                        if (passError != null) {
                            Text(
                                text = passError!!,
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    modifier = Modifier
                        .focusRequester(passwordFocusRequester)
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

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { onSignInClick() },
                    modifier = Modifier
                        .focusRequester(buttonFocusRequester)
                        .onKeyEvent { event ->
                            if (event.key == Key.Enter && userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
                                onSignInClick()
                                true // Event is consumed
                            } else {
                                false // Event is not consumed
                            }
                        },
                    enabled = userEmail.isNotEmpty() && userPassword.isNotEmpty()
                ) {
                    Text(text = "Sign In")
                }
            }
        }
    }
}
