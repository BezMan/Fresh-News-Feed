package com.bez.newsfeedtabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bez.newsfeedtabs.ui.navigation.AppNavigation
import com.bez.newsfeedtabs.ui.theme.NewsFeedTabsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsFeedTabsTheme {
                AppNavigation()
            }
        }
    }
}

