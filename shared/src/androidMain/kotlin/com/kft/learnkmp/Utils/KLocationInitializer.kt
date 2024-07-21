package com.kft.learnkmp.Utils

import android.content.Context
import android.util.Log
import androidx.startup.Initializer


internal lateinit var applicationContext: Context
    private set

public object KLocationContext

public class KLocationInitializer: Initializer<KLocationContext> {
    override fun create(context: Context): KLocationContext {
        Log.e("LearnKMP", "initialize")
        applicationContext = context.applicationContext
        return KLocationContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}