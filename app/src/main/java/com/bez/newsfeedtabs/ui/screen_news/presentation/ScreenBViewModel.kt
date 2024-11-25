package com.bez.newsfeedtabs.ui.screen_news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.domain.model.ResponseState
import com.bez.newsfeedtabs.domain.usecase.FetchEntertainmentNewsPart1UseCase
import com.bez.newsfeedtabs.domain.usecase.FetchEntertainmentNewsPart2UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class ScreenBViewModel @Inject constructor(
//    private val fetchCarsNewsUseCase: FetchCarsNewsUseCase,
    private val fetchEntertainmentNewsPart1UseCase: FetchEntertainmentNewsPart1UseCase,
    private val fetchEntertainmentNewsPart2UseCase: FetchEntertainmentNewsPart2UseCase,
//    private val saveClickedNewsTitleUseCase: SaveClickedNewsTitleUseCase
) : ViewModel() {

    private val _carsNewsState = MutableStateFlow<ResponseState<List<NewsItem>>>(ResponseState.Loading)
    val carsNewsState: StateFlow<ResponseState<List<NewsItem>>> = _carsNewsState

    private val _entertainmentNewsState = MutableStateFlow<ResponseState<List<NewsItem>>>(ResponseState.Loading)
    val entertainmentNewsState: StateFlow<ResponseState<List<NewsItem>>> = _entertainmentNewsState

    private val _allNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val allNews: StateFlow<List<NewsItem>> = _allNews

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var fetchJob: Job? = null
    private val mutex = Mutex() // Mutex to control access to the list

    // Start fetching news every 5 seconds
    internal fun startFetchingNewsPeriodically() {
        if (fetchJob?.isActive == true) return // Avoid restarting if already active
        _isLoading.value = true
        fetchJob = viewModelScope.launch {
            while (true) {
                fetchCarsAndEntertainmentNews()

                // Wait for 5 seconds before triggering the fetch again
                delay(5000)
            }
        }
    }

    // Fetch Cars and Entertainment News sequentially using Mutex to avoid overwriting
    private suspend fun fetchCarsAndEntertainmentNews() {
        // Update the states to loading first
        _carsNewsState.value = ResponseState.Loading
        _entertainmentNewsState.value = ResponseState.Loading

        _isLoading.value = true

        //for testing Loading state
//        delay(2000)



        try {
//            // Fetch Cars news
//            val carsNews = fetchCarsNewsUseCase()
//            // Update state to success after fetching
//            _carsNewsState.value = ResponseState.Success(carsNews)
//
//            // Lock the mutex before updating the list
//            mutex.withLock {
//                _allNews.update { currentList ->
//                    currentList + carsNews // Append Cars items to the list
//                }
//            }

            // Fetch Entertainment news
            val entertainmentNewsPart1 = fetchEntertainmentNewsPart1UseCase()
            val entertainmentNewsPart2 = fetchEntertainmentNewsPart2UseCase()
            val entertainmentNews = entertainmentNewsPart1 + entertainmentNewsPart2
            // Update state to success after fetching
            _entertainmentNewsState.value = ResponseState.Success(entertainmentNews)

            // Lock the mutex before updating the list
            mutex.withLock {
                _allNews.update { currentList ->
                    currentList + entertainmentNews // Append Entertainment items to the list
                }
            }

        } catch (e: Exception) {
            // If error occurs, update state to Error
            _carsNewsState.value = ResponseState.Error("Failed to fetch Cars news: ${e.localizedMessage}")
            _entertainmentNewsState.value = ResponseState.Error("Failed to fetch Entertainment news: ${e.localizedMessage}")
        }

        // Stop loading once the fetch operations are complete
        _isLoading.value = false
    }

    // Handle news item click (save title to DataStore)
    fun onNewsItemClicked(newsItem: NewsItem) {
        viewModelScope.launch {
//            saveClickedNewsTitleUseCase(newsItem.title)
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
