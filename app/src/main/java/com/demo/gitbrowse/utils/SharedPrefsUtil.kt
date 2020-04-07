package com.demo.nearbyvenues.utils

import android.content.Context
import android.content.SharedPreferences


object SharedPrefsUtil {

    private var sharedPreferences: SharedPreferences? = null

    fun initialize(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        }
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences?.getString(key, defaultValue) ?: ""
    }

    fun setString(key: String, value: String) {
        sharedPreferences?.edit()?.putString(key, value)?.apply()
    }
}