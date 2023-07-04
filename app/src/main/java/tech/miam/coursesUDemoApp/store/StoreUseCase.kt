package tech.miam.coursesUDemoApp.store

import tech.miam.coursesUDemoApp.data.models.Store
import tech.miam.coursesUDemoApp.features.miam.MiamSdkHelper

class StoreUseCase(
    private val storeLocalDataSource: StoreLocalDataSource) {

    fun getCurrentStore(): Store? = storeLocalDataSource.getStore()

    fun setStore(store: Store) {
        storeLocalDataSource.setStore(store)
        MiamSdkHelper.setStore(store.id)
    }

    fun hasStore() = getCurrentStore() != null
}