package tech.miam.coursesUDemoApp.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

interface SharedPrefsInterface {
    fun <T> set(key:String, obj:T?)
    fun <T> get(key:String, clz: Class<T>):T?
    fun <T> getList(key:String, clz: Class<T>):List<T>?
    fun <T> remove(key: String)

    fun emptyStorage()

    var preferences: SharedPreferences
}

abstract class SharedPrefs(context: Context, fileName: String) : SharedPrefsInterface {

    private var gson = Gson()

    override var preferences: SharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    override fun <T> set(key: String, obj: T?) {
        val json = gson.toJson(obj)
        preferences.edit().putString(key, json).apply()
    }

    override fun <T> get(key: String, clz: Class<T>): T? {
        val json = preferences.getString(key, null) ?: return null
        return gson.fromJson(json, clz)
    }

    override fun <T> getList(key: String, clz: Class<T>): MutableList<T>? {
        val json = preferences.getString(key, null) ?: return null
        val type = ListParameterizedType(clz)
        return gson.fromJson<MutableList<T>>(json, type)
    }

    @SuppressLint("ApplySharedPref")
    override fun emptyStorage() {
        preferences.edit().clear().commit()
    }

    override fun <T> remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun contains(key: String): Boolean = preferences.contains(key)

    fun setString(key: String, value:String?) {
        preferences.edit().putString(key, value).apply()
    }

    fun setInt(key: String, value:Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun setBoolean(key: String, value:Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun setLong(key: String, value:Long) {
        preferences.edit().putLong(key, value).apply()
    }

    fun setFloat(key: String, value:Float) {
        preferences.edit().putFloat(key, value).apply()
    }

    fun getString(key: String, defaultValue:String? = null):String? = preferences.getString(key, defaultValue)
    fun getInt(key: String, defaultValue:Int = -1):Int = preferences.getInt(key, defaultValue)
    fun getBoolean(key: String, defaultValue:Boolean = false):Boolean = preferences.getBoolean(key, defaultValue)
    fun getLong(key: String, defaultValue:Long = -1L):Long = preferences.getLong(key, defaultValue)
    fun getFloat(key: String, defaultValue:Float = -1F):Float = preferences.getFloat(key, defaultValue)

    internal class ListParameterizedType(private val type: Type) : ParameterizedType {
        override fun getRawType(): Type = ArrayList::class.java
        override fun getOwnerType(): Type? = null
        override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
    }
}