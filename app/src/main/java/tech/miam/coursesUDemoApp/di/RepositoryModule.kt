package tech.miam.coursesUDemoApp.di

import org.koin.dsl.module
import tech.miam.coursesUDemoApp.features.products.ProductsRepository


val repositoryModule = module {
    single { ProductsRepository(get()) }
}