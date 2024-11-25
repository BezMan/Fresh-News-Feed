package com.bez.newsfeedtabs.domain.model

sealed class ResponseState<out T> {

    data object Loading : ResponseState<Nothing>()
    data class Success<out T>(val data: T) : ResponseState<T>()
    data class Error(val message: String?) : ResponseState<Nothing>()
}
