package tech.miam.coursesUDemoApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.miam.coursesUDemoApp.features.miam.MiamSdkHelper
import org.koin.android.ext.android.inject
import tech.miam.coursesUDemoApp.basket.BasketLocalDataSource
import tech.miam.coursesUDemoApp.utils.BadgeManager

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private val basketLocalDataSource by inject<BasketLocalDataSource>()
    private lateinit var badgeManager: BadgeManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getUserUseCase.setUserId("user_test_u")

        viewModel.getUserUseCase.getCurrentUserId()?.let {
            MiamSdkHelper.initialize(
                context = this,
                supplierId = MiamSdkHelper.SUPPLIER_ID,
                supplierOrigin = MiamSdkHelper.SUPPLIER_ORIGIN,
                storeId = viewModel.storeUseCase.getCurrentStore()?.id,
                userId = it,
                basket = basketLocalDataSource.getProducts()
            )

            /*
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    MiamSdkHelper.basketMiamRecipeCountFlow.collectLatest { recipeCount ->
                        if (recipeCount > 0) {
                            badgeManager.showBadge(
                                R.id.menu_meals,
                                recipeCount.toString(),
                                R.color.badge
                            )
                        } else {
                            badgeManager.removeBadge(R.id.menu_meals)
                        }
                    }
                }
            }*/

        }

    }
}