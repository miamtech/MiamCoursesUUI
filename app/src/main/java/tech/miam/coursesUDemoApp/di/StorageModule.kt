package tech.miam.coursesUDemoApp.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import tech.miam.coursesUDemoApp.basket.BasketLocalDataSource
import tech.miam.coursesUDemoApp.settings.UserLocalDataSource
import tech.miam.coursesUDemoApp.store.StoreLocalDataSource

val storageModule = module {
    single { UserLocalDataSource(androidApplication()) }
    single { BasketLocalDataSource(androidApplication()) }
    single { StoreLocalDataSource(androidApplication()) }
}