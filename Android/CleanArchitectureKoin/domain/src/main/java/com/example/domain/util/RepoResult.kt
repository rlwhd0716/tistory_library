package kr.or.koelsa.elsm.domain.util

sealed class RepoResult<out T : Any> {
    data class Success<out T : Any>(val data: T?) : RepoResult<T>()
    data class Error(val exception: String) : RepoResult<Nothing>()
}