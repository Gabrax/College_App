package uni_connect.screen

import ContentWithMessageBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.jan.supabase.gotrue.auth
import org.jetbrains.compose.ui.tooling.preview.Preview
import rememberMessageBarState
import uni_connect.Database.supabase

class GradesScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val auth = remember { supabase.auth }
        val messageBarState = rememberMessageBarState()

        ContentWithMessageBar(messageBarState = messageBarState){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = 0.2f)
                    .background(color = Color.Black)
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = 1.0f)
                        .padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 24.dp)
//                ) {
//                    IconButton(onClick = { navigator.replace(HomeScreen()) }) {
//                        Icon(
//                            imageVector = Icons.Filled.Settings,
//                            contentDescription = "Back Arrow Icon",
//                            tint = Color.White
//                        )
//                    }
//                }
                    Spacer(modifier = Modifier.height(24.dp))

                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp) // Set the desired height here
                            .clip(RoundedCornerShape(size = 99.dp))
                            .clickable {}
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(99.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Home",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp) // Set the desired height here
                            .clip(RoundedCornerShape(size = 99.dp))
                            .clickable {}
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(99.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Grades",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp) // Set the desired height here
                            .clip(RoundedCornerShape(size = 99.dp))
                            .clickable {}
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(99.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Events",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            SpreadsheetView()

        }
    }
}

@Composable
@Preview
fun SpreadsheetView() {
    val columns = 10 // Number of columns in the grid
    val rows = 10 // Number of rows of data

    // Sample data for the grid
    val gridData = remember {
        List(rows * columns) { "Cell ${it + 1}" }
    }

    LazyColumn(
        modifier = Modifier
            .width(780.dp)
            .padding(8.dp) // Outer padding around the column
            .offset(x = 200.dp)
    ) {
        // First year
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp) // Padding below the header
                    .padding(16.dp) // Padding inside the header
            ) {
                Text(
                    text = "First year",
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center, // Center text within the Box
                    modifier = Modifier
                        .align(Alignment.Center) // Align text in the center of the Box
                )
            }
        }

        item {
            ItemGrid("bruuuh")
        }
        item {
            ItemGrid("hello")
        }
        item {
            ItemGrid("hello")
        }
        item {
            ItemGrid("hello")
        }
        item {
            ItemGrid("hello")
        }
        item {
            ItemGrid("hello")
        }
        item {
            ItemGrid("hello")
        }
        item {
            ItemGrid("hello")
        }
        item {
            ItemGrid("hello")
        }
        item {
            ItemGrid("hello")
        }

    }
}

@Composable
fun ItemGrid(text:String){
    AnimatedBorderCard(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        gradient = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
        onCardClick = {}
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp), // Optional padding inside the Box
            contentAlignment = Alignment.CenterStart // Center content inside the Box
        ) {
            Text(
                text = text,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }
    }
}
