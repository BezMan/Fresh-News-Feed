package com.bez.newsfeedtabs.ui.screen_news.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

// ScreenB.kt
@Composable
fun ScreenB(
) {
    val viewModel: ScreenBViewModel = hiltViewModel()

    val newsItems = listOf("News Item 1", "News Item 2", "News Item 3") // Example list

    LazyColumn {
        items(newsItems) { newsItem ->
            Text(
                text = newsItem,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        viewModel.saveNewsItemTitle(newsItem)
                    }
            )
        }
    }

}
