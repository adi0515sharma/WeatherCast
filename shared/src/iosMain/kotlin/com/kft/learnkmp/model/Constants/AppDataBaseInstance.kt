package com.kft.learnkmp.model.Constants

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kft.learnkmp.model.Database.AppDatabase

object AppDataBaseInstance {

    fun getAppDatabase(): RoomDatabase.Builder<AppDatabase>? {
       return null
    }
}