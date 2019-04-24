package com.japaneseverbquery.util

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.japaneseverbquery.JVQApp

/**
 * Created by bixiaopeng on 2017/9/21.
 */
object ReportHelper {

    private val firebaseAnalyzer: FirebaseAnalytics = FirebaseAnalytics.getInstance(JVQApp.application!!.applicationContext)

    fun reportFirstInput(word: String) {
        val b = Bundle()
        b.putString("word", word)
        firebaseAnalyzer.logEvent("first_input", b)
    }

    fun reportSelectWord(word: String) {
        val b = Bundle()
        b.putString("word", word)
        firebaseAnalyzer.logEvent("word_selected", b)
    }

    fun reportNoMatchInput(input: String) {
        val b = Bundle()
        b.putString("input", input)
        firebaseAnalyzer.logEvent("no_match_input", b)
    }
}