package tech.miam.coursesUDemoApp.basket;

import ai.mealz.core.handler.LogHandler
import ai.mealz.core.model.SupplierProduct
import ai.mealz.core.subscription.publisher.BasketPublisher
import ai.mealz.core.subscription.subscriber.BasketSubscriber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tech.miam.coursesUDemoApp.data.models.Attributes
import tech.miam.coursesUDemoApp.data.models.Product

// this is a sample class of what your basket most likely looks like
class ExampleState(
    var items: List<Product> = listOf(),
    var recipeCount: Int = 0
) {
    fun add(newProduct: Product) {
        items = items + newProduct
    }

    fun getItem(index: Int): Product? {
        return items.getOrNull(index)
    }

    fun replaceItem(index: Int, newProduct: Product) {
        if (index !in items.indices) return // do nothing if out of bounds
        items = items.mapIndexed { currentIndex, existingItem ->
            if (currentIndex == index) newProduct else existingItem
        }
    }

    fun removeItem(index: Int) {
        if (index !in items.indices) return // do nothing if out of bounds
        items = items.filterIndexed { currentIndex, _ ->
            currentIndex != index
        }
    }
}

var pretendExampleState = ExampleState(
    items = listOf(),
    recipeCount = 0
)

class BasketService : BasketPublisher, BasketSubscriber,
    CoroutineScope by CoroutineScope(Dispatchers.Main)  {

    private val retailerBasketSubject: MutableStateFlow<ExampleState> = MutableStateFlow(pretendExampleState)

    fun setBasket(products: MutableList<Product>) {
        retailerBasketSubject.value.items = products
    }

    override fun receive(event: List<SupplierProduct>) {
        pushProductToRetailer(event)
    }

    private fun pushProductToRetailer(retailerProducts: List<SupplierProduct>) {
        retailerProducts.forEach { rp ->
            val productToUpdateIdx = retailerBasketSubject.value.items.indexOfFirst { it.id == rp.id }
            if (productToUpdateIdx == -1) {
                val product = Product(
                        rp.id,
                        attributes = Attributes(
                                name = rp.name ?: "a name",
                        image = rp.imageURL ?: "",
                        extId = rp.id,
                        price = 1.0
                    ),
                quantity = rp.quantity
                )
                retailerBasketSubject.value.add(product)
            } else if (rp.quantity == 0) {
                retailerBasketSubject.value.removeItem(productToUpdateIdx)
            } else {
                retailerBasketSubject.value.replaceItem(
                    index = productToUpdateIdx,
                    newProduct = retailerBasketSubject.value.items[productToUpdateIdx].copy(
                        quantity = rp.quantity
                    )
                )
            }
        }

        launch {
            retailerBasketSubject.emit(
                ExampleState(
                    retailerBasketSubject.value.items,
                    retailerBasketSubject.value.recipeCount
                )
            )
        }
    }

    override var initialValue: List<SupplierProduct> = retailerBasketSubject.value.items.map {
        SupplierProduct(
            it.id,
            it.quantity
        )
    }

    override fun onBasketUpdate(sendUpdateToSDK: (List<SupplierProduct>) -> Unit) {
        launch {
            retailerBasketSubject.collect { state ->
                LogHandler.debug("[New Mealz] push basket update from supplier")
                sendUpdateToSDK(state.items.map { SupplierProduct(it.id, it.quantity) })
            }
        }
    }
}
