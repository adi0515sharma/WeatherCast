package com.kft.learnkmp.model.Constants

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kft.learnkmp.model.Database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.internal.synchronized

object AppDataBaseInstance {


    fun getAppDatabase(context: Context): AppDatabase {

            return  Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "word_database"
            ).build()

    }
}