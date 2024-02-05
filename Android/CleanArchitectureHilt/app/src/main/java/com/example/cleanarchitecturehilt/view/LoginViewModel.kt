package com.example.cleanarchitecturehilt.view

import com.example.cleanarchitecturehilt.domain.model.request.Login
import com.example.cleanarchitecturehilt.domain.usecase.GetUserUseCase
import com.example.cleanarchitecturehilt.domain.usecase.LoginUseCase
import com.example.cleanarchitecturehilt.lib.ext.logE
import com.example.cleanarchitecturehilt.lib.ui.AbsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val insertUserUseCase: GetUserUseCase
) : AbsViewModel() {

    fun userLogin() {
        loginUseCase(
            Login(
                id = "test",
                pw = "test"
            )
        ).useCaseResultVM { response ->
            response?.apply {
                if (isSuccess()) {

                } else {
                    logE(message)
                }
            }
        }
    }
}