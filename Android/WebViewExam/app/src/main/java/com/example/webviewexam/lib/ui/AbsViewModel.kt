package com.example.webviewexam.lib.ui

import android.content.Intent
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webviewexam.domain.util.UseCaseResult
import com.example.webviewexam.lib.ext.logE
import com.example.webviewexam.lib.util.Empty
import com.example.webviewexam.lib.util.EventLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class AbsViewModel : ViewModel(), LifecycleObserver {
    val TAG = javaClass.simpleName
    val toast = EventLiveData<Any>()
    val startActivity = EventLiveData<Pair<Class<*>, Boolean>>()
    val startActivityIntent = EventLiveData<Triple<Intent.() -> Unit, Class<*>, Boolean>>()
    val backPressed = EventLiveData<Empty>()
    val isLoading = MutableLiveData<Boolean>()
    val finish = EventLiveData<Empty>()
    val finishAffinity = EventLiveData<Empty>()
    val isKeyboard = EventLiveData<Boolean>()

    val showAlertDialog = EventLiveData<
            Triple<Pair<String, String>, // <타이틀, 메세지>
                    Pair<String, (() -> Unit)>?, // <확인버튼명, 확인시 실행> 2번째를 null 넣으면 확인버튼 사라짐.
                    Pair<String, (() -> Unit)>?>>() // <취소버튼명, 취소시 실행> 3번째를 null 넣으면 취소버튼 사라짐.


    /*
    * UseCaseResult를 ViewModel에서 사용시 고차함수로 치환
    * */
    protected fun <T : Any> Flow<UseCaseResult<T>>.useCaseResultVM(
        isShowProgress: Boolean = true,
        loading: (() -> Unit)? = null,
        complete: (() -> Unit)? = null,
        error: ((String) -> Unit)? = null,
        success: (T?) -> Unit,
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
                        logE("success::: $success")
                        it.data.also(success)
                    }

                    is UseCaseResult.Error -> {
                        logE("exception::: ${it.exception}")
                        error?.run { invoke(it.exception) }
                    }

                    else -> {
                        logE("exception::: $it")
                    }
                }
            } catch (e: Exception) {
                logE("exception::: $e")
            }
        }.launchIn(viewModelScope)

    }
}