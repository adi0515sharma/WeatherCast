package com.kft.learnkmp.SharedDI

import com.kft.learnkmp.domain.WeatherApiInterface
import com.kft.learnkmp.model.Database.AppDatabase
import com.kft.learnkmp.model.Database.getDatabaseInstance
import com.kft.learnkmp.model.WeatherApiImpl
import com.kft.learnkmp.ui.MainActivityViewModel
import org.koin.dsl.module

val SharedModule = module {
    single<WeatherApiInterface> { WeatherApiImpl() }
    single<AppDatabase?>{ getDatabaseInstance().getDatabaseInstance }

}