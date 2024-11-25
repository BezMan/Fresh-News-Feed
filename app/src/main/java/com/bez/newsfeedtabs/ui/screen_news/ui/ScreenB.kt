package com.bez.newsfeedtabs.ui.screen_news.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.domain.model.ResponseState
import com.bez.newsfeedtabs.ui.navigation.NavigationUtils

@Composable
fun ScreenB(viewModel: ScreenBViewModel = hiltViewModel()) {
    val carsNewsState by viewModel.carsNewsState.collectAsState()
    val entertainmentNewsState by viewModel.entertainmentNewsState.collectAsState()
    val allNews by viewModel.allNews.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Show loading indicator while fetching
    if (isLoading) {
        CircularProgressIndicator()
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        // Show Cars news
        when (carsNewsState) {
            is ResponseState.Loading -> {
                item { Text("Loading Cars News...") }
            }
            is ResponseState.Success -> {
                items((carsNewsState as ResponseState.Success).data) { newsItem ->
                    NewsItemView(newsItem = newsItem, onClick = { viewModel.onNewsItemClicked(newsItem) })
                }
            }
            is ResponseState.Error -> {
                item { Text("Error loading Cars news: ${(carsNewsState as ResponseState.Error).message}") }
            }
        }

        // Show Entertainment news
        when (entertainmentNewsState) {
            is ResponseState.Loading -> {
                item { Text("Loading Entertainment News...") }
            }
            is ResponseState.Success -> {
                items((entertainmentNewsState as ResponseState.Success).data) { newsItem ->
                    NewsItemView(newsItem = newsItem, onClick = { viewModel.onNewsItemClicked(newsItem) })
                }
            }
            is ResponseState.Error -> {
                item { Text("Error loading Entertainment news: ${(entertainmentNewsState as ResponseState.Error).message}") }
            }
        }

        // Show all news combined at the end
        if (allNews.isNotEmpty()) {
            items(allNews) { newsItem ->
                NewsItemView(newsItem = newsItem, onClick = { viewModel.onNewsItemClicked(newsItem) })
            }
        }
    }
}

@Composable
fun NewsItemView(newsItem: NewsItem, onClick: (NewsItem) -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick(newsItem)
                NavigationUtils.openWebLink(context, newsItem.link) // Open the web link
            },
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = newsItem.title, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = newsItem.description ?: "No description available",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


