package tech.miam.coursesUDemoApp.features.app

import android.content.Context
import androidx.startup.Initializer
import com.example.androidDemoAppXml.utils.logger.TimberDebugTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import tech.miam.coursesUDemoApp.di.apiModule
import tech.miam.coursesUDemoApp.di.storageModule
import tech.miam.coursesUDemoApp.di.useCaseModule
import tech.miam.coursesUDemoApp.di.viewModelModule
import timber.log.Timber

class KoinInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        startKoin {
            AndroidLogger()
            androidContext(context)
            modules(
                listOf(
                    apiModule,
                    storageModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}

class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Timber.plant(TimberDebugTree())
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}