package com.kft.learnkmp.model.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kft.learnkmp.model.Database.SaveList.SavedListDAO
import com.kft.learnkmp.model.Database.SaveList.SavedListDTO
import com.kft.learnkmp.model.Database.WatchList.WatchListDAO
import com.kft.learnkmp.model.Database.WatchList.WatchListDTO

@Database(
    entities = [WatchListDTO::class, SavedListDTO::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getWatchListDao() : WatchListDAO
    abstract fun getSavedListDao() : SavedListDAO
}


