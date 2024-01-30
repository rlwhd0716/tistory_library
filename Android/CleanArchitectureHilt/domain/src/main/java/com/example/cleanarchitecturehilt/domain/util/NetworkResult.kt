package com.example.cleanarchitecturehilt.domain.util

sealed class NetworkResult<out T: Any> {
    data object Loading: NetworkResult<Nothing>()
    data class Success<out T: Any>(val data: T): NetworkResult<T>()
    data class Error<out T: Any>(val exception: T): NetworkResult<T>()
    data object Complete: NetworkResult<Nothing>()
}
