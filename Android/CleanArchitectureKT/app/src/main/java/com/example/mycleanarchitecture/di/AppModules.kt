package kr.or.koelsa.elsm.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModules = listOf(repoModule, retrofitModule, useCaseModule, viewModelModule)