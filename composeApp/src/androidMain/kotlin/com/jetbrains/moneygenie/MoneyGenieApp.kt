package com.jetbrains.moneygenie

import android.app.Application
import android.content.Context
import com.jetbrains.moneygenie.data.local.RealmManager
import com.jetbrains.moneygenie.di.initKoin
import org.koin.mp.KoinPlatform.getKoin

/**
 * Created by Kundan on 18/08/24
 **/
class MoneyGenieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin()
    }

    override fun onTerminate() {
        super.onTerminate()
        getKoin().get<RealmManager>().closeRealm()
    }

    companion object {
        private var instance: MoneyGenieApp? = null

        fun getContext(): Context {
            return instance ?: throw IllegalStateException("Application not initialized yet")
        }
    }
}