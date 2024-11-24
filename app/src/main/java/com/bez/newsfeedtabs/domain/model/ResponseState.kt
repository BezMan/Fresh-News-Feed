package com.bez.newsfeedtabs.domain.model

sealed class ResponseState {
    data object Loading : ResponseState()
    data class Success(val newsFeed: NewsFeed) : ResponseState()
    data class Error(val message: String) : ResponseState()
}
