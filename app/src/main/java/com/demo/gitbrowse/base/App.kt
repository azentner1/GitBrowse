package com.demo.gitbrowse.base

import android.app.Application
import com.demo.gitbrowse.koin.appModules
import com.demo.nearbyvenues.utils.SharedPrefsUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModules)
        }
        SharedPrefsUtil.initialize(this@App)
    }
}
