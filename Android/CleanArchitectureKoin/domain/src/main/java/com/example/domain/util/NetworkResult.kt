package com.example.domain.util

sealed class NetworkResult {
    object Loading : UseCaseResult<Nothing>()
    data class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    data class Error(val exception: String) : UseCaseResult<Nothing>()
    object Complete : UseCaseResult<Nothing>()
}
