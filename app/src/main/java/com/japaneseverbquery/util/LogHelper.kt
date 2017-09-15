package com.japaneseverbquery.util

import android.util.Log

import com.japaneseverbquery.BuildConfig

/**
 * Created by bixiaopeng on 2017/9/15.
 */

object LogHelper {
    private var sTag: String? = null
    private val mLogEnabled = BuildConfig.DEBUG_LOG
    private val shouldThrowException = BuildConfig.DEBUG

    fun setTag(tag: String) {
        sTag = tag
    }

    fun i(subTag: String, msg: String) {
        if (mLogEnabled) {
            Log.i(sTag, getLogMsg(subTag, msg))
        }
    }

    private fun getLogMsg(subTag: String, msg: String): String {
        val sb = StringBuffer()
                .append("{").append(Thread.currentThread().name).append("}")
                .append("[").append(subTag).append("] ")
                .append(msg)

        return sb.toString()
    }
}
