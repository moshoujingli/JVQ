package com.japaneseverbquery.util

import android.content.Context
import com.japaneseverbquery.JVQApp

/**
 * Created by bixiaopeng on 2017/9/21.
 */
object AppPreference {
    private val sharedPreference = JVQApp.application!!.getSharedPreferences("g_pref", Context.MODE_PRIVATE)
    fun isFirstInputContent(): Boolean {
        return if (sharedPreference.contains("first_input")) {
            false
        } else {
            sharedPreference.edit().putBoolean("first_input", false).apply()
            true
        }
    }

    fun needShowPrivacy(): Boolean {
        return if (sharedPreference.contains("ShownPrivacy")) {
            false
        } else {
            sharedPreference.edit().putBoolean("ShownPrivacy", true).apply()
            true
        }
    }
}