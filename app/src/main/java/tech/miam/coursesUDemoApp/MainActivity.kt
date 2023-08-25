package tech.miam.coursesUDemoApp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.miam.core.localisation.I18nResolver
import com.miam.kmmMiamCore.handler.ContextHandlerInstance
import com.miam.kmmMiamCore.handler.ReadyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.miam.coursesUDemoApp.features.miam.template.MiamSdkHelper
import org.koin.android.ext.android.inject
import tech.miam.coursesUDemoApp.basket.BasketLocalDataSource
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private val basketLocalDataSource by inject<BasketLocalDataSource>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MiamSdkHelper.initialize(
            context = this,
            supplierId = 7,
            supplierOrigin = "app.courseu.com",
            storeId = "25910",
            userId = "test_${UUID.randomUUID()}",
            basket = basketLocalDataSource.getProducts()
        )
        I18nResolver.registerContext(this.applicationContext)
        CoroutineScope(Dispatchers.Main).launch {
            ContextHandlerInstance.instance.observeReadyEvent().collect {
                when (it) {
                    ReadyEvent.isReady -> {
                        val navHostFragment = findViewById<FragmentContainerView>(R.id.nav_host_fragment)
                        navHostFragment.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
        }
    }
}