package uni_connect.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideOrientation
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.resources.painterResource
import university_connect.composeapp.generated.resources.Icon
import university_connect.composeapp.generated.resources.Res


class MainScreen: Screen {
    @Composable
    override fun Content() {

        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            TabNavigator(HomeTab) {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            TabNavigationItem(tab = HomeTab)
                            TabNavigationItem(tab = Grades)
                            TabNavigationItem(tab = Logout)
                        }
                    }
                ){
                    Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())){
                        CurrentTab()
                    }
                }
            }
        }

        if(!isLoggedIn){
            Navigator(LoginScreen()){ navigator ->
                SlideTransition(navigator, orientation = SlideOrientation.Vertical) }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab){
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {},
        label = {
            Text(text = tab.options.title)
        }


    )
}

class TestScreen1: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
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
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(Res.drawable.Icon),
                    contentDescription = "Zodiac Image"
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp) // Set the desired height here
                        .clip(RoundedCornerShape(size = 99.dp))
                        .clickable { navigator.replace(HomeScreen()) }
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
                        .clickable { navigator.replace(HomeScreen()) }
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
                        .clickable { navigator.replace(HomeScreen()) }
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


                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp) // Set the desired height here
                        .clip(RoundedCornerShape(size = 99.dp))
                        .clickable { navigator.replace(HomeScreen()) }
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(99.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Logout",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}