package tech.miam.coursesUDemoApp.di

import tech.miam.coursesUDemoApp.basket.BasketService

object LocalDI {
    internal val basketService: BasketService = BasketService()
}