package com.kft.learnkmp.android.di

import com.kft.learnkmp.model.Constants.AppDataBaseInstance
import com.kft.learnkmp.model.Database.AppDatabase
import com.kft.learnkmp.model.Database.getDatabaseInstance
import com.kft.learnkmp.ui.MainActivityViewModel
import com.kft.learnkmp.ui.Next5DaysForecastViewModel
import com.kft.learnkmp.ui.WeatherConditionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModuleDI = module{
    viewModel { MainActivityViewModel(get(), get()) }
    viewModel { Next5DaysForecastViewModel(get()) }
    viewModel { WeatherConditionViewModel(get(), get()) }
}