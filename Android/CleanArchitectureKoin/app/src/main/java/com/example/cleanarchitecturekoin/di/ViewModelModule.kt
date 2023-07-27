package com.example.cleanarchitecturekoin.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.cleanarchitecturekoin.view.*


val viewModelModule = module {
    viewModelOf(::MainViewModel)
}