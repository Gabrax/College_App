package uni_connect.screen

import ContentWithMessageBar
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.jan.supabase.gotrue.auth
import rememberMessageBarState
import uni_connect.Database.currentUserProfile
import uni_connect.Database.supabase

@Composable
fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = 0.dp),
    borderWidth: Dp = 2.dp,
    gradient: Brush = Brush.sweepGradient(listOf(Color.Gray, Color.White)),
    animationDuration: Int = 10000,
    onCardClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Infinite Colors"
    )

    Surface(
        modifier = modifier
            .clip(shape)
            .clickable { onCardClick() },
        shape = shape
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(borderWidth)
                .drawWithContent {
                    rotate(degrees = degrees) {
                        drawCircle(
                            brush = gradient,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            color = MaterialTheme.colorScheme.surface,
            shape = shape
        ) {
            content()
        }
    }
}

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val auth = remember { supabase.auth }
        val messageBarState = rememberMessageBarState()

        ContentWithMessageBar(messageBarState = messageBarState){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top

            ){
                currentUserProfile.value.forEach{ user ->
                    Text(
                        fontSize = MaterialTheme.typography.displaySmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        text = "Welcome, ${user.name} ${user.surname}",
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Spacer(Modifier.width(30.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 3 columns
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            ) {
                item {
                    AnimatedBorderCard(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(all = 24.dp),
                        shape = RoundedCornerShape(8.dp),
                        gradient = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
                        onCardClick = {}
                    ) {
                        Column(modifier = Modifier.padding(all = 24.dp)) {
                            Text(
                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                fontWeight = FontWeight.Bold,
                                text = "Welcome"
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                text = "Lorem ipsum dolor sit amet"
                            )
                        }
                    }
                }
                item {
                    AnimatedBorderCard(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(all = 24.dp),
                        shape = RoundedCornerShape(8.dp),
                        gradient = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
                        onCardClick = {}
                    ) {
                        Column(modifier = Modifier.padding(all = 24.dp)) {
                            Text(
                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                fontWeight = FontWeight.Bold,
                                text = "Hello"
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                text = "Lorem ipsum dolor sit amet"
                            )
                        }
                    }
                }
                item {
                    AnimatedBorderCard(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(all = 24.dp),
                        shape = RoundedCornerShape(8.dp),
                        gradient = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
                        onCardClick = {}
                    ) {
                        Column(modifier = Modifier.padding(all = 24.dp)) {
                            Text(
                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                fontWeight = FontWeight.Bold,
                                text = "Hello"
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                text = "Lorem ipsum dolor sit amet"
                            )
                        }
                    }
                }
                item {
                    AnimatedBorderCard(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(all = 24.dp),
                        shape = RoundedCornerShape(8.dp),
                        gradient = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
                        onCardClick = {}
                    ) {
                        Column(modifier = Modifier.padding(all = 24.dp)) {
                            Text(
                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                fontWeight = FontWeight.Bold,
                                text = "Hello"
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                text = "Lorem ipsum dolor sit amet"
                            )
                        }
                    }
                }
                item {
                    AnimatedBorderCard(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(all = 24.dp),
                        shape = RoundedCornerShape(8.dp),
                        gradient = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
                        onCardClick = {}
                    ) {
                        Column(modifier = Modifier.padding(all = 24.dp)) {
                            Text(
                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                fontWeight = FontWeight.Bold,
                                text = "Hello"
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                text = "Lorem ipsum dolor sit amet"
                            )
                        }
                    }
                }
                item {
                    AnimatedBorderCard(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(all = 24.dp),
                        shape = RoundedCornerShape(8.dp),
                        gradient = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
                        onCardClick = {}
                    ) {
                        Column(modifier = Modifier.padding(all = 24.dp)) {
                            Text(
                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                fontWeight = FontWeight.Bold,
                                text = "Hello"
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                text = "Lorem ipsum dolor sit amet\nLorem ipsum dolor sit amet" +
                                        "Lorem ipsum dolor sit amet\nLorem ipsum dolor sit amet"
                            )
                        }
                    }
                }


//                Button(
//                    onClick = {
//                        navigator.push(DetailsScreen())
//                    }
//                ) {
//                    Text(text = "Check Details")
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))

                //DEBUG
//                val currentUser = auth.currentUserOrNull()
//                println(currentUser?.id)


            }

        }
    }
}