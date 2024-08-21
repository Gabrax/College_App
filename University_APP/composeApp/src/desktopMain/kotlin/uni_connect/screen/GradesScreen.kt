package uni_connect.screen

import ContentWithMessageBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import rememberMessageBarState
import uni_connect.Database.GradeData
import uni_connect.Database.fetchCurrUserGrades
import uni_connect.Database.grades
import uni_connect.Database.supabase

class GradesScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val auth = remember { supabase.auth }
        val messageBarState = rememberMessageBarState()

        val user = auth.currentUserOrNull()

        val gradeList = grades.value

        val listSize = gradeList.size


        ContentWithMessageBar(messageBarState = messageBarState){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = 0.2f)
                    .background(color = Color.Black)
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                    Spacer(modifier = Modifier.height(24.dp))

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .clip(RoundedCornerShape(99.dp))
                            .padding(horizontal = 12.dp, vertical = 0.dp),
                        onClick = {
                            scope.launch {
                                if (user != null) {
                                    user.email?.let { fetchCurrUserGrades(it)
                                        println(listSize)
                                    }
                                }
                            }
                        },
                        enabled = true,
                        shape = RoundedCornerShape(99.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Refresh",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .clip(RoundedCornerShape(99.dp))
                            .padding(horizontal = 12.dp, vertical = 0.dp),
                        onClick = {  },
                        enabled = true,
                        shape = RoundedCornerShape(99.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .clip(RoundedCornerShape(99.dp))
                            .padding(horizontal = 12.dp, vertical = 0.dp),
                        onClick = {  },
                        enabled = true,
                        shape = RoundedCornerShape(99.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

            }
            SpreadsheetView()

        }
    }
}

@Composable
@Preview
fun SpreadsheetView() {

    val gradeList = grades.value

    LazyColumn(
        modifier = Modifier
            .width(780.dp)
            .padding(8.dp)
            .offset(x = 200.dp)
    ) {

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "First year",
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

        items(gradeList.indices.toList()) { index ->
            ItemGrid(grades = grades, index = index)
        }

    }
}


@Composable
fun ItemGrid(grades: State<List<GradeData>>, index: Int) {
    val gradeData = grades.value.getOrNull(index)

    AnimatedBorderCard(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        gradient = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
        onCardClick = {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = createGradeAnnotatedString(gradeData),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

fun createGradeAnnotatedString(gradeData: GradeData?): AnnotatedString {
    return buildAnnotatedString {
        if (gradeData != null) {
            val className = gradeData.class_name
            val gradesList = listOf(
                gradeData.grade1, gradeData.grade2, gradeData.grade3,
                gradeData.grade4, gradeData.grade5, gradeData.grade6, gradeData.grade7
            )

            append("$className: ")
            gradesList.forEachIndexed { index, grade ->
                val color = when {
                    grade == null -> Color.Transparent
                    grade < 3 -> Color.Red
                    else -> Color.Green
                }

                withStyle(SpanStyle(color = color)) {
                    append(grade?.toString() ?: " ")
                }

                if(index < gradesList.size - 1) {
                    append(" || ")
                }
            }
        } else {
            append("No data available")
        }
    }
}


