package tech.miam.coursesUDemoApp.features.miam.template

import android.content.Context
import com.miam.kmmMiamCore.handler.Basket.BasketHandler
import com.miam.kmmMiamCore.handler.Basket.BasketHandlerInstance
import com.miam.kmmMiamCore.handler.ContextHandlerInstance
import com.miam.kmmMiamCore.handler.GroceriesListHandler
import com.miam.kmmMiamCore.handler.PointOfSaleHandler
import com.miam.kmmMiamCore.handler.UserHandler
import com.miam.kmmMiamCore.miam_core.model.RetailerProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tech.miam.coursesUDemoApp.basket.BasketEvent
import tech.miam.coursesUDemoApp.data.models.Product
import tech.miam.coursesUDemoApp.features.products.ProductsRepository
import timber.log.Timber

object MiamSdkHelper : CoroutineScope by CoroutineScope(Dispatchers.Main), KoinComponent {
    private const val TAG = "MiamSdkHelper"

    private var isInitialized = false
    private lateinit var applicationContext: Context

    private lateinit var userId: String
    private var supplierId: Int = -1
    private lateinit var supplierOrigin: String
    private lateinit var storeId: String
    private var enableUserProfiling: Boolean = true

    private var retailerBasketSubject = MutableStateFlow<MutableList<Product>>(mutableListOf())

    private var _basketMiamRecipeCountFlow = MutableStateFlow(0)
    val basketMiamRecipeCountFlow = _basketMiamRecipeCountFlow.asStateFlow()

    private var _basketMiamFlow = MutableStateFlow<BasketEvent>(BasketEvent.Loading)
    val basketMiamFlow = _basketMiamFlow.asStateFlow()

    private var basketHandler: BasketHandler = BasketHandlerInstance.instance

    /** should not be changed during session */
    private var enableLike: Boolean = true

    private val productsRepository by inject<ProductsRepository>()

    fun initialize(
        context: Context,
        supplierId: Int,
        supplierOrigin: String,
        basket: MutableList<Product>,
        userId: String,
        storeId: String
    ) = apply {

        if (userId.isBlank()) throw Exception("userId Cannot be null or empty or blank")
        if (storeId.isBlank()) throw Exception("storeId Cannot be null or empty or blank")
        if (supplierOrigin.isBlank()) throw Exception("supplierOrigin Cannot be null or empty or blank")

        MiamTemplateManager()
        if (isInitialized) return@apply
        applicationContext = context.applicationContext
        isInitialized = true

        setSupplier(supplierId, supplierOrigin)
        setUser(userId)
        setStore(storeId)
        setEnableLike(enableLike)
        setUserProfiling(enableUserProfiling)

        setBasket(basket)
        setListenToRetailerBasket(basketHandler)
        setPushProductToBasket(basketHandler)

        ContextHandlerInstance.instance.setContext(applicationContext)

        launch {
            ContextHandlerInstance.instance.observeReadyEvent().collect {
                Timber.tag(TAG).i("🟢 Miam SDK is initialized and ready to be used")
            }
        }

        launch {
            GroceriesListHandler.getRecipeCountChangeFlow().collect {
                Timber.tag(TAG).d("recipes count by flow : $it")
                _basketMiamRecipeCountFlow.emit(it.newRecipeCount)
            }
        }

        GroceriesListHandler.onRecipeCountChange {
            Timber.tag(TAG).d("recipes count by callback : $it")
            launch {
                _basketMiamRecipeCountFlow.emit(it)
            }
        }
    }

    /******************************************************************
     *
     * Mandatory
     *
     *****************************************************************/

    /**
     * Set the current supplier
     * @param id the supplier id (retailer name)
     * @param origin the platform using Miam
     */
    fun setSupplier(id: Int, origin: String) = apply {
        checkIfSdkInitialized({
            supplierId = id
            supplierOrigin = origin
            PointOfSaleHandler.setSupplierOrigin(supplierOrigin)
            PointOfSaleHandler.setSupplier(supplierId)
        })
    }

    /**
     * Set the current store of the current user
     * @param id the current store id
     */
    fun setStore(id: String) = apply {
        storeId = id
        PointOfSaleHandler.updateStoreId(storeId)
    }

    /**
     * Set the current user
     * @param id the current user id
     */
    fun setUser(id: String) = apply {
        checkIfSdkInitialized({
            userId = id
            UserHandler.updateUserId(id)
            //TODO(Julien) maybe we should restart as we change user during session,
            // then we need to restart as data are different from one user to another one
        })
    }

    /******************************************************************
     *
     * Optional
     *
     *****************************************************************/

    /**
     * Enable if allow to learn from the customer's choices and suggest more and more specific
     * recipes and products
     * @param enable true or false, the default value is true
     */
    fun setUserProfiling(enable: Boolean) = apply {
        enableUserProfiling = enable
        UserHandler.setProfilingAllowed(enableUserProfiling)
    }

    /**
     * Allow to display likes on recipes
     * @param enable true or false according if we want to show like on recipes. Should be set only
     * at Miam SDK initialization
     */
    private fun setEnableLike(enable: Boolean) {
        UserHandler.setEnableLike(enable)
    }

    /**
     * Check if Miam SDK is well initialized before doing some actions
     * @param whenTrue the lambda to execute if the Miam SDK is well initialized
     * @param whenFalse the lambda to execute if the Miam SDK is not initialized yet
     */
    private fun checkIfSdkInitialized(
        whenTrue: () -> Unit,
        whenFalse: () -> Unit = { throw IllegalAccessException("We must init the Miam SDK well") }
    ) {
        if (!isInitialized) {
            whenFalse()
        } else {
            whenTrue()
        }
    }

    /******************************************************************
     *
     * Basket
     *
     *****************************************************************/

    fun setBasket(products: MutableList<Product>) {
        retailerBasketSubject = MutableStateFlow(products)
    }

    private fun setListenToRetailerBasket(basketHandler: BasketHandler) {
        with(basketHandler) {
            setListenToRetailerBasket(MiamSdkHelper::initBasketListener)
            pushProductsToMiamBasket(retailerBasketSubject.value.map { product ->
                retailProductToMiamRetailerProduct(product)
            })
        }
    }

    private fun initBasketListener() {
        launch {
            retailerBasketSubject.collect {
                Timber.tag(TAG).d("Basket emitted")
                basketHandler.pushProductsToMiamBasket(it.map { product ->
                    retailProductToMiamRetailerProduct(product)
                })
            }
        }
    }

    private fun retailProductToMiamRetailerProduct(product: Product): RetailerProduct {
        return RetailerProduct(
            retailerId = product.id,
            productIdentifier = product.identifier,
            quantity = product.quantity,
            name = product.attributes.name
        )
    }

    private fun setPushProductToBasket(basketHandler: BasketHandler) {
        basketHandler.setPushProductsToRetailerBasket(MiamSdkHelper::pushProductToRetailer)
    }

    private fun pushProductToRetailer(retailerProducts: List<RetailerProduct>) {
        retailerProducts.forEach { rp ->
            val productToUpdateIdx = retailerBasketSubject.value.indexOfFirst { it.id == rp.retailerId }
            if (productToUpdateIdx == -1) {
                runBlocking {
                    withContext(Dispatchers.Default) {
                        productsRepository.getProduct(rp.retailerId).body()?.data
                    }?.let {
                        retailerBasketSubject.value.add(it.copy(quantity = rp.quantity))
                    }
                }
            } else if (rp.quantity == 0) {
                retailerBasketSubject.value.removeAt(productToUpdateIdx)
            } else {
                retailerBasketSubject.value[productToUpdateIdx] =
                    retailerBasketSubject.value[productToUpdateIdx].copy(quantity = rp.quantity)
            }
        }

        launch {
            retailerBasketSubject.emit(retailerBasketSubject.value)
            _basketMiamFlow.emit(BasketEvent.Changed(retailerBasketSubject.value))
        }
    }
}