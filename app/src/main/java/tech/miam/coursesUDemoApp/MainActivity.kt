package tech.miam.coursesUDemoApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miam.core.localisation.I18nResolver
import com.miam.kmmMiamCore.handler.ContextHandlerInstance
import com.miam.kmmMiamCore.handler.ReadyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.miam.coursesUDemoApp.features.miam.template.MiamSdkHelper
import org.koin.android.ext.android.inject
import tech.miam.coursesUDemoApp.basket.BasketLocalDataSource

class MainActivity : AppCompatActivity() {
    private val basketLocalDataSource by inject<BasketLocalDataSource>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MiamSdkHelper.initialize(
            context = this,
            supplierId = 7,
            supplierOrigin = "app.courseu.com",
            storeId = "25910",
            userId = "u_test_user",
            basket = basketLocalDataSource.getProducts()
        )
        I18nResolver.registerContext(this.applicationContext)
        CoroutineScope(Dispatchers.Main).launch {
            ContextHandlerInstance.instance.observeReadyEvent().collect {
                when (it) {
                    ReadyEvent.isReady -> {
                        setContentView(R.layout.activity_main)
                    }
                    else -> {}
                }
            }
        }
    }
}