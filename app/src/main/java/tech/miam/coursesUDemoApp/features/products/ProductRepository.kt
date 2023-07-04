package tech.miam.coursesUDemoApp.features.products

import tech.miam.coursesUDemoApp.data.ProductService

class ProductsRepository(private val productService: ProductService) {
    suspend fun getProduct(productId: String) = productService.getProduct(productId)
}