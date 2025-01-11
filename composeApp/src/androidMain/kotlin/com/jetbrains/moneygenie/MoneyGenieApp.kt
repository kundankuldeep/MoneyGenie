package com.jetbrains.moneygenie

import android.app.Application
import com.jetbrains.moneygenie.data.local.RealmManager
import com.jetbrains.moneygenie.di.initKoin
import org.koin.mp.KoinPlatform.getKoin

/**
 * Created by Kundan on 18/08/24
 **/
class MoneyGenieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    override fun onTerminate() {
        super.onTerminate()
        getKoin().get<RealmManager>().closeRealm()
    }
}