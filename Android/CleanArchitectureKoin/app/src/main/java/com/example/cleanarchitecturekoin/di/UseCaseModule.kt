package com.example.cleanarchitecturekoin.di

import com.example.domain.usecase.*
import com.example.domain.usecaseimpl.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val useCaseModule = module {
    singleOf(::MainUseCaseImpl) { bind<MainUseCase>() }

}