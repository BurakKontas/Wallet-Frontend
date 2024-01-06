package com.aburakkontas.wallet.services

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext

class LocalStorage private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun removeData(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    companion object {
        private const val PREF_NAME = "Wallet-LocalStorage"

        @Volatile
        private var instance: LocalStorage? = null
        fun getInstance(context: Context): LocalStorage =
            instance ?: synchronized(this) {
                instance ?: LocalStorage(context).also { instance = it }
            }
    }
}
