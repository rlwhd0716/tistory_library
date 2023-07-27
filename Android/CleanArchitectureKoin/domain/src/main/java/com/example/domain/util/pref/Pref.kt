package com.example.domain.util.pref

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.domain.util.toJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@SuppressLint("StaticFieldLeak")
object Pref {
    private var context: Context? = null
    private const val PREFERENCE_NAME = "_pref"
    lateinit var prefs: SharedPreferences
        private set
    private lateinit var editor: SharedPreferences.Editor

    @SuppressLint("CommitPrefEdits")
    fun initializeApp(context: Context, name: () -> String = { context.packageName.replace('.', '_') + PREFERENCE_NAME }) {
        Pref.context = context
        prefs = context.getSharedPreferences(name(), Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    @JvmStatic
    fun putStringEncryptionExtra(key: String, value: String) {
        context?.run {
            try {
                editor.putString(key, AES256Chiper.chiperAesEncoding(this, value))
            } catch (e: Exception) {
                editor.putString(key, "")
            }
            editor.commit()
        }
    }

    @JvmStatic
    fun getStringEncryptionExtra(key: String, defaultValue: String): String {
        return context?.run {
            try {
                AES256Chiper.chiperAesDecode(this, getStringExtra(key, defaultValue))
            } catch (e: Exception) {
                defaultValue
            }
        } ?: run { defaultValue }
    }

    @JvmStatic
    fun putIntExtra(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    @JvmStatic
    fun putFloatExtra(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.commit()
    }

    @JvmStatic
    fun putStringExtra(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    @JvmStatic
    fun putLongExtra(key: String, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    @JvmStatic
    fun putBooleanExtra(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    @JvmStatic
    fun getIntExtra(key: String, defValue: Int = 0): Int {
        return prefs.getInt(key, defValue)
    }

    @JvmStatic
    fun getFloatExtra(key: String, defValue: Float = 0f): Float {
        return prefs.getFloat(key, defValue)
    }

    @JvmStatic
    fun getStringExtra(key: String, defValue: String = ""): String {
        return prefs.getString(key, defValue).takeUnless { it.isNullOrEmpty() } ?: run { "" }
    }

    @JvmStatic
    fun getLongExtra(key: String, defValue: Long = 0): Long {
        return prefs.getLong(key, defValue)
    }

    @JvmStatic
    fun getBooleanExtra(key: String, defValue: Boolean = false): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    inline fun <reified T> put(key: String, value: T) {
        when (value) {
            is Boolean -> putBooleanExtra(key, value)
            is Float -> putFloatExtra(key, value)
            is Int -> putIntExtra(key, value)
            is Long -> putLongExtra(key, value)
            is String -> putStringExtra(key, value)
            else -> putStringExtra(key, value.toJson())
        }
    }

    inline fun <reified T> get(key: String, defValue: T, type: Type = object : TypeToken<T>() {}.type): T {
        return (when (defValue) {
            is Boolean -> getBooleanExtra(key, defValue)
            is Float -> getFloatExtra(key, defValue)
            is Int -> getIntExtra(key, defValue)
            is Long -> getLongExtra(key, defValue)
            is String -> getStringExtra(key, defValue)
            else -> Gson().fromJson(getStringExtra(key, ""), type)
        }) as T
    }

    @JvmStatic
    fun removePreference(key: String) {
        editor.remove(key).commit()
    }

    @JvmStatic
    fun containCheck(key: String): Boolean {
        return prefs.contains(key)
    }
}
