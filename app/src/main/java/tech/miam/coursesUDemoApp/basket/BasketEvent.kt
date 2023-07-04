package tech.miam.coursesUDemoApp.basket

import tech.miam.coursesUDemoApp.data.models.Product

sealed class BasketEvent {
    object Loading : BasketEvent()
    class Changed(val products: List<Product>) : BasketEvent()
    class Added(val product: Product) : BasketEvent()
    class Removed(val product: Product) : BasketEvent()
    class Updated(val product: Product) : BasketEvent()
    object Cleaned : BasketEvent()
    class Error(val e: Throwable) : BasketEvent()
}