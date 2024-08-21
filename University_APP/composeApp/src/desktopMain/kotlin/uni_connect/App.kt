package uni_connect

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideOrientation
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import uni_connect.screen.*


var exitAppFlag by mutableStateOf(false)

@Composable
@Preview
fun App() {

    val lightTheme = lightColorScheme(
        primary = primaryLight,
        onPrimary = onPrimaryLight,
        primaryContainer = primaryContainerLight,
        onPrimaryContainer = onPrimaryContainerLight,
    )
    val darkTheme = darkColorScheme(
        primary = primaryDark,
        onPrimary = onPrimaryDark,
        primaryContainer = primaryContainerDark,
        onPrimaryContainer = onPrimaryContainerDark,
    )

    val colors by mutableStateOf(if (isSystemInDarkTheme()) darkTheme else lightTheme)

    MaterialTheme(colorScheme = colors) {
        Navigator(LoginScreen()) { SlideTransition(it, orientation = SlideOrientation.Vertical) }
    }
}

