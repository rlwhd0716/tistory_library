package com.example.cleanarchitecturehilt.domain.util

import android.os.Parcelable
import android.util.Log
import kotlinx.coroutines.flow.channelFlow

open class BaseUseCase {
    val TAG = javaClass.simpleName

    fun <T: Parcelable> setChannelFlow(block: suspend () -> RepoResult<T>) = channelFlow {
        trySend(UseCaseResult.Loading)
//        Log.e(TAG, "network try")
        block.invoke().let {
            when (it) {
                is RepoResult.Success -> trySend(UseCaseResult.Success(it.data))
                is RepoResult.Error -> trySend(UseCaseResult.Error(it.exception))
            }
        }
//        Log.e(TAG, "network process")
        trySend(UseCaseResult.Complete)
//        Log.e(TAG, "network complete")
    }

    fun <ANY: Any> setChannelFlowAny(block: suspend () -> RepoResult<ANY>) = channelFlow {
        trySend(UseCaseResult.Loading)
        Log.e(TAG, "network try")
        block.invoke().let {
            when (it) {
                is RepoResult.Success -> trySend(UseCaseResult.Success(it.data))
                is RepoResult.Error -> trySend(UseCaseResult.Error(it.exception))
            }
        }
        Log.e(TAG, "network process")
        trySend(UseCaseResult.Complete)
        Log.e(TAG, "network complete")
    }
}