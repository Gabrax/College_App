package uni_connect.screen

import ContentWithMessageBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
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
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rememberMessageBarState
import uni_connect.Database.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class NameSurname: Screen{

    @Composable
    override fun Content() {
        val messageBarState = rememberMessageBarState()
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()

        val auth = remember { supabase.auth }
        val storage = remember { supabase.storage }
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

        var pathSingleChosen by remember { mutableStateOf("") }
        var showFilePicker by remember { mutableStateOf(false) }
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        val fileType = listOf("jpg", "png", "jpeg")

        lateinit var filepath: File

        val configureClick = {
            scope.launch {
                try {
                    val currentSession = auth.currentUserOrNull()
                    if (currentSession != null) {
                        insertDisplayName(userName,userSurname)
                        messageBarState.addSuccess("Successfully logged in")
                        delay(1500L)
                        fetchCurrentUsername()
                        fetchCurrUserImage()
                        currentSession.email?.let { fetchCurrUser1YGrades(it) }
                        navigator.replace(MainScreen())
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

                OutlinedButton(
                    onClick = {
                        showFilePicker = true
                    },
                    modifier = Modifier.size(150.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, Color.Black),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
                ) {
                    imageBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap,
                            contentDescription = null, // Provide a content description for accessibility
                            modifier = Modifier
                        )
                    }
                }

                if (showFilePicker) {
                    FilePicker(show = showFilePicker, fileExtensions = fileType) { file ->
                        pathSingleChosen = file?.path ?: "none selected"
                        filepath = File(pathSingleChosen)
                        val bufferedImage: BufferedImage = ImageIO.read(filepath)
                        imageBitmap = bufferedImage.toComposeImageBitmap()
                        showFilePicker = false
                    }
                }

                Text("Enter your\ndisplay name:",
                    modifier = Modifier.wrapContentSize(Alignment.Center),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )

                //Spacer(modifier = Modifier.height(25.dp))

                LaunchedEffect(Unit) {
                    nameFocusRequester.requestFocus()
                }

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
                    onClick = {
                                scope.launch {
                                    try{
                                        configureClick()
                                        val gimmeemail = auth.currentUserOrNull()
                                            val filename = gimmeemail?.email
                                            val bucket = storage.from("userimages")
                                            if (filename != null) {
                                                bucket.upload(filename,filepath,upsert = false)
                                            }
                                            fetchCurrUserImage()

                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                            }
                              },
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




