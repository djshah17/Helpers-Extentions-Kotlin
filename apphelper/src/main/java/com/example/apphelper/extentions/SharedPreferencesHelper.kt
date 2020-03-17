package com.example.apphelper.extentions

import android.content.SharedPreferences
import androidx.core.content.edit

fun <T> SharedPreferences.put(key: String, t: T) {
    when (t) {
        is Int -> putInt(key, t)
        is Long -> putLong(key, t)
        is Float -> putFloat(key, t)
        is String -> putString(key, t)
        is Boolean -> putBoolean(key, t)
    }
}

fun SharedPreferences.putString(key: String, value: String) {
    edit { putString(key, value) }
}

fun SharedPreferences.putInt(key: String, value: Int) {
    edit { putInt(key, value) }
}

fun SharedPreferences.putBoolean(key: String, value: Boolean) {
    edit { putBoolean(key, value) }
}

fun SharedPreferences.putFloat(key: String, value: Float) {
    edit { putFloat(key, value) }
}

fun SharedPreferences.putLong(key: String, value: Long) {
    edit { putLong(key, value) }
}

inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T): T =
    when (defaultValue) {
        is Boolean -> getBoolean(key, defaultValue) as? T ?: defaultValue
        is Int -> getInt(key, defaultValue) as? T ?: defaultValue
        is Float -> getFloat(key, defaultValue) as? T? ?: defaultValue
        is Long -> getLong(key, defaultValue) as? T? ?: defaultValue
        is String -> getString(key, defaultValue) as? T? ?: defaultValue
        else -> throw UnsupportedOperationException("Class not supported by SharedPreferences.put()")
    }

fun SharedPreferences.remove(key: String) {
    edit { remove(key) }
}

fun SharedPreferences.clear() {
    edit { clear() }
}