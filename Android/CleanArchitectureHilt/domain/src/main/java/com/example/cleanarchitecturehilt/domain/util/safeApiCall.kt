package com.example.cleanarchitecturehilt.domain.util

import android.os.Parcelable
import retrofit2.Response

/*
* service의 suspend 함수를 받아 RepoResult로 반환
* */
suspend fun <T : Parcelable> safeApiCall(call: suspend () -> Response<T>): RepoResult<T> {
    return try {
        call.invoke().let {
            if (it.isSuccessful) {
                RepoResult.Success(it.body())
            } else {
                val error = when (it.code()) {
                    403 -> "Authentication failed"
                    else -> "error"
                }
                RepoResult.Error(it.errorBody()?.string() ?: error)
            }
        }
    } catch (e: Exception) {
        RepoResult.Error(e.message ?: "Internet error runs")
    }
}

suspend fun <T: Parcelable> safeDBCall(call: suspend () -> T): RepoResult<T> {
    return try {
        call.invoke().let {
            RepoResult.Success(it)
        }
    } catch (e: Exception) {
        RepoResult.Error(e.message ?: "Database error runs")
    }
}


/*
* service의 suspend 함수를 받아 RepoResult로 반환
* */
suspend fun <ANY : Any> safeApiCallAny(call: suspend () -> Response<ANY>): RepoResult<ANY> {
    return try {
        call.invoke().let {
            if (it.isSuccessful) {
                RepoResult.Success(it.body())
            } else {
                val error = when (it.code()) {
                    403 -> "Authentication failed"
                    else -> "error"
                }
                RepoResult.Error(it.errorBody()?.string() ?: error)
            }
        }
    } catch (e: Exception) {
        RepoResult.Error(e.message ?: "Internet error runs")
    }
}

suspend fun <T : Any, U : Any> safeApiCallMap(
    call: suspend () -> Response<T>,
    response: suspend (Response<T>) -> U
): RepoResult<U> {
    return try {
        call.invoke().let {
            if (it.isSuccessful) {
                RepoResult.Success(response(it))
            } else {
                val error = when (it.code()) {
                    403 -> "Authentication failed"
                    else -> "error"
                }
                RepoResult.Error(it.errorBody()?.string() ?: error)
            }
        }
    } catch (e: Exception) {
        RepoResult.Error(e.message ?: "Internet error runs")
    }
}