package com.namndev.network

import android.app.Application
import com.namndev.network.monitoring.ConnectivityStateHolder

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ConnectivityStateHolder.getInstance().registerConnectivityBroadcaster(this)
    }
}