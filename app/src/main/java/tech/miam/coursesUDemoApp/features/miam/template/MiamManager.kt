package tech.miam.coursesUDemoApp.features.miam.template

import android.content.Context
import com.miam.core.Mealz
import com.miam.core.handler.LogHandler
import com.miam.core.init.basket
import com.miam.core.init.subscriptions
import com.miam.core.init.sdkRequirement
import com.miam.core.model.SupplierProduct
import com.miam.core.subscription.publisher.BasketPublisher
import com.miam.core.subscription.subscriber.BasketSubscriber
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
import tech.miam.coursesUDemoApp.features.miam.template.MiamSdkHelper.pushProductToRetailer
import tech.miam.coursesUDemoApp.features.products.ProductsRepository
import tech.miam.coursesuui.config.MiamTemplateManager
import timber.log.Timber

object MiamSdkHelper : CoroutineScope by CoroutineScope(Dispatchers.Main), KoinComponent, BasketPublisher, BasketSubscriber {
    private const val TAG = "MiamSdkHelper"

    private var isInitialized = false
    private lateinit var applicationContext: Context

    private lateinit var userId: String
    private lateinit var storeId: String
    private var enableUserProfiling: Boolean = true

    private var retailerBasketSubject = MutableStateFlow<MutableList<Product>>(mutableListOf())

    private var _basketMiamRecipeCountFlow = MutableStateFlow(0)
    val basketMiamRecipeCountFlow = _basketMiamRecipeCountFlow.asStateFlow()

    private var _basketMiamFlow = MutableStateFlow<BasketEvent>(BasketEvent.Loading)
    val basketMiamFlow = _basketMiamFlow.asStateFlow()

    val supplierKey = "ewoJInN1cHBsaWVyX2lkIjogIjciLAoJInBsYXVzaWJsZV9kb21haW5lIjogIm1pYW0uY291cnNlc3UuYXBwIiwKCSJtaWFtX29yaWdpbiI6ICJjb3Vyc2VzdSIsCgkib3JpZ2luIjogIm1pYW0uY291cnNlc3UuYXBwIiwKCSJtaWFtX2Vudmlyb25tZW50IjogIlBST0QiCn0"



    /** should not be changed during session */
    private var enableLike: Boolean = true

    private val productsRepository by inject<ProductsRepository>()

    fun initialize(
        appContext: Context,
        basket: MutableList<Product>,
        userId: String,
        storeId: String
    ) = apply {

        if (userId.isBlank()) throw Exception("userId Cannot be null or empty or blank")
        if (storeId.isBlank()) throw Exception("storeId Cannot be null or empty or blank")

        if (isInitialized) return@apply
        applicationContext = appContext.applicationContext
        startMealz(this)
        MiamTemplateManager()
        isInitialized = true
        setUser(userId)
        setStore(storeId)
        setEnableLike(enableLike)
        setUserProfiling(enableUserProfiling)
        setBasket(basket)
        Mealz.environment.setAllowsSponsoredProducts(true)

        Mealz.notifications.availability.listen {
                if(it)  Timber.tag(TAG).i("🟢 Miam SDK is on and ready to be used")
                else    Timber.tag(TAG).i("🔴 Miam SDK is off and not ready to be used")
        }

        Mealz.notifications.recipesCount.listen {
            Timber.tag(TAG).d("recipes count by flow : $it")
            launch {
                _basketMiamRecipeCountFlow.emit(it)
            }
        }

    }

    private fun startMealz(miamSdkHelper: MiamSdkHelper) {
        Mealz.Core() {
            sdkRequirement {
                key = supplierKey
                context = applicationContext
            }
            subscriptions {
                basket  {
                    // Listen to Miam's basket updates
                    subscribe(miamSdkHelper)
                    // Push client basket notifications
                    register(miamSdkHelper)
                }
            }
        }
    }

    /******************************************************************
     *
     * Mandatory
     *
     *****************************************************************/



    /**
     * Set the current store of the current user
     * @param id the current store id
     */
    fun setStore(id: String) = apply {
        storeId = id
        Mealz.user.setStoreId(storeId)
    }

    /**
     * Set the current user
     * @param id the current user id
     */
    fun setUser(id: String) = apply {
        checkIfSdkInitialized({
            userId = id
            Mealz.user.updateUserId(userId)
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
        Mealz.user.setProfilingAllowance(enableUserProfiling)
    }

    /**
     * Allow to display likes on recipes
     * @param enable true or false according if we want to show like on recipes. Should be set only
     * at Miam SDK initialization
     */
    private fun setEnableLike(enable: Boolean) {
        Mealz.user.setEnableLike(enable)
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

    private fun productToSupplierProduct(product: Product): SupplierProduct  {
        return SupplierProduct(
            id = product.id,
            productIdentifier = product.identifier,
            quantity = product.quantity,
            name = product.attributes.name
        )
    }
    override fun receive(event: List<SupplierProduct>) {
        pushProductToRetailer(event)
    }

    private fun pushProductToRetailer(retailerProducts: List<SupplierProduct>) {
        retailerProducts.forEach { rp ->
            val productToUpdateIdx = retailerBasketSubject.value.indexOfFirst { it.id == rp.id }
            if (productToUpdateIdx == -1) {
                runBlocking {
                    withContext(Dispatchers.Default) {
                        productsRepository.getProduct(rp.id).body()?.data
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

    override var initialValue: List<SupplierProduct> = retailerBasketSubject.value.map { productToSupplierProduct(it) }


    override fun onBasketUpdate(sendUpdateToSDK: (List<SupplierProduct>) -> Unit) {
        launch {
            retailerBasketSubject.collect { state ->
                LogHandler.debug("[New Mealz] push basket update from supplier")
                sendUpdateToSDK(state.map { SupplierProduct(it.id, it.quantity) })
            }
        }
    }


}