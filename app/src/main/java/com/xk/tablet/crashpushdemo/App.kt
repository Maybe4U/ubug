package com.xk.tablet.crashpushdemo

import android.app.Application
import com.xk.tablet.ubug.UBug

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UBug.instance.init(this)
    }
}