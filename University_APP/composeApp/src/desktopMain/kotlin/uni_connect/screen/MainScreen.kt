package uni_connect.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideOrientation
import cafe.adriel.voyager.transitions.SlideTransition


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

