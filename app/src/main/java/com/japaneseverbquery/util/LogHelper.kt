package com.japaneseverbquery.util

import android.util.Log

import com.japaneseverbquery.BuildConfig

/**
 * Created by bixiaopeng on 2017/9/15.
 */

object LogHelper {
    private val ERROR_STATE = "error_state"
    private var sTag: String = BuildConfig.APPLICATION_ID
    private val mLogEnabled = BuildConfig.DEBUG_LOG
    private val shouldThrowException = BuildConfig.DEBUG


    fun errorState(msg: String, throwable: Throwable?) {
        if (shouldThrowException) {
            throw Exception(msg, throwable)
        } else {
            e(ERROR_STATE, msg)
        }
    }

    fun setTag(tag: String) {
        sTag = tag
    }

    fun e(subTag: String, msg: String) {
        if (mLogEnabled) {
            Log.e(sTag, getLogMsg(subTag, msg))
        }
    }

    fun i(subTag: String, msg: String) {
        if (subTag.startsWith("af_debug_tag")){
            Log.i(sTag, getLogMsg(subTag, msg))
        }else{
            if (mLogEnabled) {
                Log.i(sTag, getLogMsg(subTag, msg))
            }
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
