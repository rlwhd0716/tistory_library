package com.example.domain.util.pref

import android.content.SharedPreferences
import android.util.Log
import com.example.domain.util.toJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T> SharedPreferences.delegate(
    defaultValue: T,
    crossinline key: (KProperty<*>) -> String = KProperty<*>::name,
    type: Type = object : TypeToken<T>() {}.type
): ReadWriteProperty<Any, T> =
    object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return (when (defaultValue) {
                is Boolean -> getBoolean(key(property), defaultValue)
                is Float -> getFloat(key(property), defaultValue)
                is Int -> getInt(key(property), defaultValue)
                is Long -> getLong(key(property), defaultValue)
                is String -> getString(key(property), defaultValue)
                else -> Gson().fromJson(getString(key(property), ""), type)
            } as T).also {
                Log.d("domain", "key : ${key(property)}, get : $it")
            }
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            Log.d("domain", "key : ${key(property)}, value : $value")
            with(edit()) {
                when (value) {
                    is Boolean -> putBoolean(key(property), value)
                    is Float -> putFloat(key(property), value)
                    is Int -> putInt(key(property), value)
                    is Long -> putLong(key(property), value)
                    is String -> putString(key(property), value)
                    else -> putString(key(property), value.toJson())
                }.apply()
            }

        }
    }

fun SharedPreferences.delegateEncrypt(
    defaultValue: String,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, String> =
    object : ReadWriteProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            return Pref.getStringEncryptionExtra(key(property), defaultValue).also {
                Log.d("domain", "key : ${key(property)}, get : $it")
            }
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            Log.d("domain", "key : ${key(property)}, value : $value")
            Pref.putStringEncryptionExtra(key(property), value)
        }
    }
