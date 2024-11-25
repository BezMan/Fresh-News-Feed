package com.bez.newsfeedtabs.ui.screen_news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.domain.model.ResponseState
import com.bez.newsfeedtabs.domain.usecase.FetchEntertainmentPart1UC
import com.bez.newsfeedtabs.domain.usecase.FetchEntertainmentPart2UC
import com.bez.newsfeedtabs.domain.usecase.StoreLastNewsClickedUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class ScreenBViewModel @Inject constructor(
    private val fetchEntertainmentPart1UC: FetchEntertainmentPart1UC,
    private val fetchEntertainmentPart2UC: FetchEntertainmentPart2UC,
    private val storeLastNewsClickedUC: StoreLastNewsClickedUC
) : ViewModel() {

    private val _entertainmentNewsState =
        MutableStateFlow<ResponseState<List<NewsItem>>>(ResponseState.Loading)
    val entertainmentNewsState: StateFlow<ResponseState<List<NewsItem>>> = _entertainmentNewsState

    val itemsList: MutableList<NewsItem> = mutableListOf()


    private var fetchJob: Job? = null
    private val mutex = Mutex() // Mutex to control access to the list

    // Start fetching news every 5 seconds
    internal fun startFetchingNewsPeriodically() {
        if (fetchJob?.isActive == true) return // Avoid restarting if already active
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
        itemsList.clear()
        // Update the states to loading first
        _entertainmentNewsState.value = ResponseState.Loading

        //for testing Loading state
//        delay(2000)


        try {
            viewModelScope.launch {
                val part1Deferred = async {
                    //for testing async
//                    delay(400)

                    fetchEntertainmentPart1UC()
                }
                val part1Result = part1Deferred.await()
                updateStateWithPart1(part1Result)
            }
            viewModelScope.launch {
                val part2Deferred = async {
                    fetchEntertainmentPart2UC()
                }
                val part2Result = part2Deferred.await()
                updateStateWithPart2(part2Result)
            }
        } catch (e: Exception) {
            _entertainmentNewsState.value =
                ResponseState.Error("Failed to fetch Entertainment news: ${e.localizedMessage}")
        }
    }

    // Update state with part 1 (add to the start of the list)
    private suspend fun updateStateWithPart1(part1: List<NewsItem>) {
        mutex.withLock {
            itemsList.addAll(0, part1) // Add part1 to the start of the list
            _entertainmentNewsState.value = ResponseState.Success(itemsList) // Update state immediately
        }
    }

    // Update state with part 2 (add to the end of the list)
    private suspend fun updateStateWithPart2(part2: List<NewsItem>) {
        mutex.withLock {
            itemsList.addAll(part2) // Add part2 to the end of the list
            _entertainmentNewsState.value = ResponseState.Success(itemsList) // Update state immediately
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
