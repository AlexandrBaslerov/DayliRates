package com.example.dailyrates.data

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val prefModule = module {
    single { Preferences(PreferenceManager.getDefaultSharedPreferences(androidContext())) }
}

class Preferences(private val defaultSharedPreferences: SharedPreferences) {

    companion object {
        private const val SETTINGS_KEY = "is_first_loading"
    }

    fun setValueFirstLoading(value: Boolean) =
        defaultSharedPreferences.edit().putBoolean(SETTINGS_KEY, value).apply()

    fun isFirstLoading(): Boolean = defaultSharedPreferences.getBoolean(SETTINGS_KEY, true)

    fun clear() {
        defaultSharedPreferences.edit().clear().apply()
    }
}

