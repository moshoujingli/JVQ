package com.japaneseverbquery

import android.app.Application
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.japaneseverbquery.util.LogHelper

/**
 * Created by bixiaopeng on 2017/9/15.
 */

class JVQApp : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        startAppsflyer()
    }

    companion object {
        const val AF_DEBUG_TAG = "af_debug_tag"
        var application: JVQApp? = null
            private set
    }

    private fun startAppsflyer() {
        var listener = object : AppsFlyerConversionListener {
            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {

                LogHelper.i(AF_DEBUG_TAG, p0.toString())
            }

            override fun onAttributionFailure(p0: String?) {
                LogHelper.i(AF_DEBUG_TAG, p0.toString())
            }

            override fun onInstallConversionDataLoaded(p0: MutableMap<String, String>?) {
                LogHelper.i(AF_DEBUG_TAG, p0.toString())
            }

            override fun onInstallConversionFailure(p0: String?) {
                LogHelper.i(AF_DEBUG_TAG, p0.toString())
            }

        }
        AppsFlyerLib.getInstance().init("cE5Ac4a3oKGCfpF7okAKnJ", listener, applicationContext)
        AppsFlyerLib.getInstance().setDebugLog(BuildConfig.DEBUG)
        AppsFlyerLib.getInstance().startTracking(this)
    }

}
