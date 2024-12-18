package com.bez.newsfeedtabs.ui.screen_news.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.domain.model.ResponseState
import com.bez.newsfeedtabs.navigation.NavigationUtils

@Composable
fun ScreenB(viewModel: ScreenBViewModel = hiltViewModel()) {

    val entertainmentNewsState by viewModel.entertainmentNewsState.collectAsState()

    // Observe the lifecycle and handle onStop event (e.g., Home button click)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                viewModel.stopFetching()
            }
            if (event == Lifecycle.Event.ON_START) {
                viewModel.startFetchingNewsPeriodically() // Optional: restart fetching if needed
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Wrap the entire screen in a Box for overlaying the loading indicator
    Box() {
        LazyColumn(modifier = Modifier.padding(16.dp)) {

            // Show Entertainment news
            when (entertainmentNewsState) {
                is ResponseState.Loading -> {
                    item { Text("Loading Entertainment News...") }
                }
                is ResponseState.Success -> {
                    items((entertainmentNewsState as ResponseState.Success).data) { newsItem ->
                        NewsItemView(
                            newsItem = newsItem,
                            onItemClick = { viewModel.onNewsItemClicked(newsItem) })
                    }
                }
                is ResponseState.Error -> {
                    item { Text("Error loading Entertainment news: ${(entertainmentNewsState as ResponseState.Error).message}") }
                }
            }

        }

        // Show loading indicator centered over the content when data is being fetched
        if (entertainmentNewsState is ResponseState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun NewsItemView(newsItem: NewsItem, onItemClick: (NewsItem) -> Unit) {
    val context = LocalContext.current

    // Card that displays the news item
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onItemClick(newsItem)

                newsItem.link?.let {
                    NavigationUtils.openWebLink(context, it) // Open the web link
                }
            },
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = newsItem.title ?: "no title", style = MaterialTheme.typography.bodyLarge)
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
