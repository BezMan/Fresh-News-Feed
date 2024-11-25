package com.bez.newsfeedtabs.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bez.newsfeedtabs.ui.screen_info.presentation.ScreenA
import com.bez.newsfeedtabs.ui.screen_news.presentation.ScreenB

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Set up NavHost to navigate between screens
    NavHost(navController = navController, startDestination = "screenA") {
        composable("screenA") {
            ScreenA(onNavigateToScreenB = {
                navController.navigate("screenB")
            })
        }
        composable("screenB") {
            ScreenB()
        }
    }
}
