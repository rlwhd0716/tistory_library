package com.example.domain.usecaseimpl

import com.example.domain.repository.MainRepo
import com.example.domain.usecase.MainUseCase
import com.example.domain.util.BaseUseCase

class MainUseCaseImpl(private val repo: MainRepo): BaseUseCase(), MainUseCase {
    override fun test() = setChannelFlow {
        repo.test()
    }
}