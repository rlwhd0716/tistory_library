package com.example.cleanarchitecturekoin.di

import com.example.domain.repository.*
import com.example.data.repositoryimpl.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val repoModule = module {
    singleOf(::MainRepoImpl) { bind<MainRepo>() }
}