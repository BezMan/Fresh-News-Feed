package com.bez.newsfeedtabs.ui.screen_news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.domain.model.ResponseState
import com.bez.newsfeedtabs.domain.usecase.FetchEntertainmentUC
import com.bez.newsfeedtabs.domain.usecase.StoreLastNewsClickedUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenBViewModel @Inject constructor(
    private val fetchEntertainmentUC: FetchEntertainmentUC,
    private val storeLastNewsClickedUC: StoreLastNewsClickedUC
) : ViewModel() {

    private val _entertainmentNewsState =
        MutableStateFlow<ResponseState<List<NewsItem>>>(ResponseState.Loading)
    val entertainmentNewsState: StateFlow<ResponseState<List<NewsItem>>> = _entertainmentNewsState

    private var fetchJob: Job? = null

    // Start fetching news every 5 seconds
    fun startFetchingNewsPeriodically() {
        if (fetchJob?.isActive == true) return
        fetchJob = viewModelScope.launch {
            while (isActive) {
                fetchEntertainmentUC.fetchEntertainmentNews()
                    .onStart { _entertainmentNewsState.value = ResponseState.Loading }
                    .catch { e -> _entertainmentNewsState.value = ResponseState.Error(e.localizedMessage ?: "Unknown error") }
                    .collect { news -> _entertainmentNewsState.value = ResponseState.Success(news) }

                delay(5000) // Wait before fetching again
            }
        }
    }

    // Handle news item click (save title to DataStore)
    fun onNewsItemClicked(newsItem: NewsItem) {
        viewModelScope.launch {
            storeLastNewsClickedUC.execute(newsItem.title ?: "undefined title")
        }
    }

    // Stop the periodic fetch job
    fun stopFetching() {
        fetchJob?.cancel()
    }

    // Cleanup job when view model is cleared
    override fun onCleared() {
        super.onCleared()
        stopFetching()
    }
}
