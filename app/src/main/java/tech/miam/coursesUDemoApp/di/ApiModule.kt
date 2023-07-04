package tech.miam.coursesUDemoApp.di

import tech.miam.coursesUDemoApp.data.ProductService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single { provideRetrofit(get(), get()) }
    single { provideOkHttpClient() }
    single { provideGson() }

    factory { createApi<ProductService>(get()) }
}

private const val TIMEOUT = 30_000L

private fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson) =
    Retrofit.Builder().baseUrl("https://api.miam.tech/api/v1/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient).build()

private fun provideOkHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
        .apply {
            connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)

            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("ContentType", "application/vnd.api+json")
                    builder.header("Accept", "*/*")
                    builder.header("miam-front-type", "app")
                    builder.header("miam-front-version", "3.10.0")
                    builder.header("miam-api-version", "4.8.0")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.HEADERS
    logging.level = HttpLoggingInterceptor.Level.BODY
    client.addInterceptor(logging)

    return client.build()
}

private fun provideGson(): Gson = GsonBuilder().serializeNulls().create()

private inline fun <reified T> createApi(retrofit: Retrofit) = retrofit.create(T::class.java)
