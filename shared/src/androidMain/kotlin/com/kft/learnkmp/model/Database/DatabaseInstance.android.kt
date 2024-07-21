package com.kft.learnkmp.model.Database

import androidx.room.Room
import com.kft.learnkmp.Utils.applicationContext
import com.kft.learnkmp.model.Constants.AppDataBaseInstance
import com.kft.learnkmp.model.Constants.AppDataBaseInstance.getAppDatabase
import com.kft.learnkmp.model.Database.AppDatabase
import com.kft.learnkmp.model.Database.DatabaseInstance
import com.kft.learnkmp.model.Ktor.KtorClient
import io.ktor.client.HttpClient


class AndroidDatabaseInstance() : DatabaseInstance {
    override val getDatabaseInstance: AppDatabase? = getAppDatabase(applicationContext)
}
actual fun getDatabaseInstance(): DatabaseInstance = AndroidDatabaseInstance()