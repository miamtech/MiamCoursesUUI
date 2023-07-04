package tech.miam.coursesUDemoApp.basket

import android.content.Context
import tech.miam.coursesUDemoApp.data.SharedPrefs
import tech.miam.coursesUDemoApp.data.models.Product

class BasketLocalDataSource(context: Context) : SharedPrefs(context, FILENAME) {
    companion object {
        const val FILENAME = "BasketLocalDataSource"
        const val KEY_BASKET_PRODUCTS = "basket_products"
    }

    override fun emptyStorage() {
        preferences.edit().clear().apply()
    }

    fun getProducts(): MutableList<Product> = getList(KEY_BASKET_PRODUCTS, Product::class.java) ?: mutableListOf()

    fun setProducts(products:List<Product>) {
        set(KEY_BASKET_PRODUCTS, products)
    }

    fun addProduct(product: Product) {
        val products = getProducts()
        val productToAdd: Product? = products.firstOrNull { it.id == product.id }
        if(productToAdd != null) {
            productToAdd.quantity++
        } else {
            products.add(product)
        }

        setProducts(products)
    }

    fun removeProduct(product: Product) {
        val products = getProducts()
        val productToRemove: Product? = products.firstOrNull { it.id == product.id }
        if(productToRemove != null) {
            if(productToRemove.quantity > 1) {
                productToRemove.quantity--
            } else {
                products.removeAll {
                    it.id == product.id
                }
            }
        }

        setProducts(products)
    }

    fun updateProduct(product: Product) {
        val products = getProducts()
        val productToUpdate: Product? = products.firstOrNull { it.id == product.id }
        if(productToUpdate != null) {
            productToUpdate.quantity = product.quantity
        }

        setProducts(products)
    }
}