package expo.modules.bashgmuhce

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

import java.util.Map
import java.util.HashMap

object RNStorage{
    const val HCE_ID = "HCE_ID"
    const val HCE_AID = "HCE_AID"

    private const val DEFAULT_VALUE = "F01020304050"
    private val valueLock = Any()

    fun setValue(context: Context, value: String, key: String) {
        synchronized(valueLock) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putString(key, value).apply()
        }
    }

    fun getValue(context: Context, key: String): String {
        synchronized(valueLock) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(key, DEFAULT_VALUE) ?: DEFAULT_VALUE
        }
    }
}