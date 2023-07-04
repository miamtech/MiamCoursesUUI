package tech.miam.coursesUDemoApp.store

import android.content.Context
import tech.miam.coursesUDemoApp.data.SharedPrefs
import tech.miam.coursesUDemoApp.data.models.Store

class StoreLocalDataSource(context: Context) : SharedPrefs(context, FILENAME) {
    companion object {
        const val FILENAME = "StoreLocalDataSource"
        const val KEY_STORE = "store"
    }

    override fun emptyStorage() {
        preferences.edit().clear().apply()
    }

    fun getStore(): Store? = get(KEY_STORE, Store::class.java)

    fun setStore(store: Store) {
        set(KEY_STORE, store)
    }
}