package com.kft.learnkmp.android

import android.app.Application
import com.kft.learnkmp.SharedDI.SharedModule
import com.kft.learnkmp.android.di.appModuleDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(SharedModule, appModuleDI)
        }
    }
}

