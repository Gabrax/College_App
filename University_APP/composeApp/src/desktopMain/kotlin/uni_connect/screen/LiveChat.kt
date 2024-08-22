package uni_connect.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import uni_connect.Database.LiveChatMessage
import uni_connect.Database.bucket
import uni_connect.Database.currentUserProfile
import uni_connect.Database.supabase


class LiveChat : Screen {

    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        val usermail = supabase.auth.currentUserOrNull()

        var liveChatMessages by remember { mutableStateOf<List<LiveChatMessage>>(emptyList()) }

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                try {
                    listenToInserts(coroutineScope) { newMessage ->
                        liveChatMessages = liveChatMessages + newMessage
                    }

                    val result = supabase.from("LiveChat").select()
                    val jsonString = result.data
                    liveChatMessages = Json { ignoreUnknownKeys = true }.decodeFromString<List<LiveChatMessage>>(jsonString)
                } catch (error: Exception) {
                    println("Error: ${error.message}")
                }
            }
        }

        ChatScreen(liveChatMessages, usermail!!.email.toString())

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var text by remember { mutableStateOf("") }

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("Enter text") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )

                Button(
                    onClick = {
                        println("Button clicked with input: $text")
                        currentUserProfile.value.forEach { user ->
                            insertMessage(user.name, user.surname, usermail.email.toString(), text)
                        }
                    }
                ) {
                    Text("Send")
                }
            }
        }
    }
}

fun insertMessage(name: String, surname: String, email: String, messageContent: String) = runBlocking {
    try {

        val message = LiveChatMessage(
            name = name,
            surname = surname,
            email = email,
            message = messageContent
        )

        supabase
            .from("LiveChat").insert(message) // Use the message object created above
    } catch (e: Exception) {
        println("Exception occurred: ${e.message}")
    }
}

@Composable
fun ChatScreen(liveChatMessages: List<LiveChatMessage>, currentUserId: String) {
    val painter = asyncPainterResource(bucket)
    val listState = rememberLazyListState()

    LaunchedEffect(liveChatMessages) {
        if (liveChatMessages.isNotEmpty()) {
            listState.scrollToItem(liveChatMessages.size - 1) // Scroll to the last item (most recent message)
        }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(liveChatMessages) { chatMessage ->
            val isCurrentUser = chatMessage.email == currentUserId
            val alignment = if (isCurrentUser) Alignment.End else Alignment.Start
            val bubbleColor = if (isCurrentUser) Color(0xFFDCF8C6) else Color(0xFFECECEC)
            val textColor = if (isCurrentUser) Color.Black else Color.Black
            val bubbleShape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = if (isCurrentUser) 0.dp else 16.dp,
                bottomStart = if (isCurrentUser) 16.dp else 0.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
            ) {
                Column(
                    modifier = Modifier
                        .background(bubbleColor, bubbleShape)
                        .padding(8.dp)
                        .widthIn(max = 300.dp),
                    horizontalAlignment = alignment
                ) {
                    if (!isCurrentUser) {
                        Text(
                            text = "${chatMessage.name} ${chatMessage.surname}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 2.dp),
                            fontSize = 12.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isCurrentUser) {
                            KamelImage(
                                resource = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.CenterVertically), // Ensures the image stays aligned with the text
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(8.dp)) // Space between the image and the text
                        }

                        Text(
                            text = chatMessage.message,
                            color = textColor,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(end = if (isCurrentUser) 5.dp else 0.dp) // Adjust padding for current user
                                .widthIn(max = 240.dp) // Set a maximum width for the text
                        )

                        if (isCurrentUser) {
                            Spacer(modifier = Modifier.width(8.dp)) // Space between the text and the image

                            KamelImage(
                                resource = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.CenterVertically), // Ensures the image stays aligned with the text
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

// Listen to inserts and pass new records back to the Composable
suspend fun listenToInserts(
    coroutineScope: CoroutineScope,
    onNewMessage: (LiveChatMessage) -> Unit
) {
    val channel = supabase.channel("LiveChat")
    val changeFlow = channel.postgresChangeFlow<PostgresAction.Insert>(schema = "public") {
        table = "LiveChat"
    }

    // Collect the flow and trigger a callback for each new message
    changeFlow.onEach {
        val newRecord = it.record.toString() // Convert the record to JSON string
        val newMessage = Json.decodeFromString<LiveChatMessage>(newRecord) // Decode the new message
        onNewMessage(newMessage) // Pass the new message to the callback
    }.launchIn(coroutineScope)

    channel.subscribe()
}