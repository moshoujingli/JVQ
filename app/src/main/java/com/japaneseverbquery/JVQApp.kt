package com.japaneseverbquery

import android.app.Application

/**
 * Created by bixiaopeng on 2017/9/15.
 */

class JVQApp : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        var application: JVQApp? = null
            private set
    }
}
