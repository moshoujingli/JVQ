package com.japaneseverbquery.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.japaneseverbquery.util.LogHelper

/**
 * Created by bixiaopeng on 2019/1/14.
 */
class InstallReferrerReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        LogHelper.i("InstallReferrerReceiver", "InstallReferrerReceiver。onReceive: ")
        val action = intent?.action
        if (TextUtils.equals(action, "com.android.vending.INSTALL_REFERRER")) {
            val referrer = intent?.extras?.getString("referrer")
            referrer?.let {
                LogHelper.i("InstallReferrerReceiver", referrer)
            }
        }
    }
}