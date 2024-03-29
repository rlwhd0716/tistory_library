package com.example.webviewexam.domain.util

sealed class UseCaseResult<out T : Any> {
    data object Loading : UseCaseResult<Nothing>()
    data class Success<out T : Any>(val data: T?) : UseCaseResult<T>()
    data class Error(val exception: String) : UseCaseResult<Nothing>()
    data object Complete : UseCaseResult<Nothing>()
}

fun <T : Any> UseCaseResult<T>.useCaseResult(loading: (() -> Unit)? = null, complete: (() -> Unit)? = null, error: ((String) -> Unit)? = null, success: (T?) -> Unit) {
    when (this) {
        is UseCaseResult.Loading -> loading?.run { invoke() }
        is UseCaseResult.Complete -> complete?.run { invoke() }
        is UseCaseResult.Success -> this.data.also(success)
        is UseCaseResult.Error -> error?.let { (it.invoke(this.exception)) }
        else -> {}
    }
}