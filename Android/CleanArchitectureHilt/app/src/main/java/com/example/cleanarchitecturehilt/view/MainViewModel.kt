package com.example.cleanarchitecturehilt.view

import com.example.cleanarchitecturehilt.domain.usecase.GetUserUseCase
import com.example.cleanarchitecturehilt.lib.ext.logE
import com.example.cleanarchitecturehilt.lib.ui.AbsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : AbsViewModel() {

    fun getUserInfo() {
        getUserUseCase("00000001").useCaseResultVM { response ->
            response?.apply {
                if (isDatabase()) {
                    logE("유저 데이터 가져옴 : $result")
                } else {
                    logE(message)
                }
            }
        }
    }
}