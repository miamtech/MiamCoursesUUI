package tech.miam.coursesUDemoApp.di

import tech.miam.coursesUDemoApp.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
}