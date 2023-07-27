package com.example.cleanarchitecturekoin.lib.ui

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.util.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.example.cleanarchitecturekoin.lib.util.Empty
import com.example.cleanarchitecturekoin.lib.util.EventLiveData

abstract class AbsViewModel : ViewModel(), LifecycleObserver {
    val TAG = javaClass.simpleName
    val toast = EventLiveData<Any>()
    val startActivity = EventLiveData<Pair<Class<*>, Boolean>>()
    val startActivityIntent = EventLiveData<Triple<Intent.() -> Unit, Class<*>, Boolean>>()
    val backPressed = EventLiveData<Empty>()
    val isLoading = MutableLiveData<Boolean>()
    val finish = EventLiveData<Empty>()
    val finishAffinity = EventLiveData<Empty>()
    val hideKeyboard = EventLiveData<Any>()
    val showAlertDialog = EventLiveData<Triple<Triple<Int, Int, Int>, Boolean, () -> Unit>>() // 타이틀, 메시지, 확인버튼, 확인시 실행할 Unit
    val loginTokenExpire = EventLiveData<Empty>()

    /*
    * UseCaseResult를 ViewModel에서 사용시 고차함수로 치환
    * */
    protected fun <T : Any> Flow<UseCaseResult<T>>.useCaseResultVM(
        isShowProgress: Boolean = true,
        loading: (() -> Unit)? = null,
        complete: (() -> Unit)? = null,
        error: ((String) -> Unit)? = null,
        success: (T?) -> Unit
    ) {
        onEach {
            try {
                when (it) {
                    UseCaseResult.Loading -> {
                        loading?.run { invoke() }
                        if (isShowProgress) isLoading.postValue(true)
                    }
                    UseCaseResult.Complete -> {
                        complete?.run { invoke() }
                        if (isShowProgress) isLoading.postValue(false)
                    }
                    is UseCaseResult.Success -> {
                        Log.e(javaClass.simpleName, "Success:: $success")
                        it.data.also(success)
                    }
                    is UseCaseResult.Error -> {
                        // 401 에러가 떨어지는 중
                        Log.e(javaClass.simpleName, "Error:: ${it.exception}")
                        error?.run { invoke(it.exception) }
                        if (it.exception.contains("E503")) {
                            loginTokenExpire.postEvent(Empty.Call)
                        } else {
                            toast.postEvent(it.exception)
                        }
                    }
                    else -> {
                        Log.e(javaClass.simpleName, "Exception:: $it")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }

        }.launchIn(viewModelScope)

    }
}