package tech.miam.coursesUDemoApp.di

import tech.miam.coursesUDemoApp.settings.GetUserUseCase
import tech.miam.coursesUDemoApp.store.StoreUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetUserUseCase)
    factoryOf(::StoreUseCase)
}