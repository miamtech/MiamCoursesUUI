package tech.miam.coursesUDemoApp.settings

import android.content.Context
import tech.miam.coursesUDemoApp.data.SharedPrefs

class UserLocalDataSource(context: Context) : SharedPrefs(context, FILENAME) {
    companion object {
        const val FILENAME = "UserLocalDataSource"
        const val KEY_USER_ID = "user_id"
    }

    override fun emptyStorage() {
        preferences.edit().clear().apply()
    }

    fun getUserId(): String? = getString(KEY_USER_ID)
    fun setUserId(userId: String) {
        set(KEY_USER_ID, userId)
    }

    fun isLogged() = getUserId() != null
}