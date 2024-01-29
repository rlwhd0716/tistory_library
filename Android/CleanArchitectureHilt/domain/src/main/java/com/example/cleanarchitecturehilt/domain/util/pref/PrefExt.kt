package com.example.cleanarchitecturehilt.domain.util.pref

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.reflect.KProperty

inline fun <reified T> pref(defaultValue: T, type: Type = object : TypeToken<T>() {}.type, noinline key: (KProperty<*>) -> String = KProperty<*>::name) = Pref.prefs.delegate(defaultValue, key, type)

fun prefEncrypt(defaultValue: String, key: (KProperty<*>) -> String = KProperty<*>::name) = Pref.prefs.delegateEncrypt(defaultValue, key)

inline fun <reified T> type() = object : TypeToken<T>() {}.type

inline fun <reified T> String.prefPut(defValue: T) = Pref.put(this, defValue)

inline fun <reified T> String.prefGet(defValue: T) = Pref.get(this, defValue)