package tech.miam.coursesUDemoApp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import tech.miam.coursesUDemoApp.data.models.ProductWrapper

interface ProductService {
    @GET("items/{id}")
    suspend fun getProduct(@Path("id") productId: String): Response<ProductWrapper>
}