package com.jetbrains.moneygenie

import android.app.Application
import com.jetbrains.moneygenie.di.initKoin

/**
 * Created by Kundan on 18/08/24
 **/
class MoneyGenieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}