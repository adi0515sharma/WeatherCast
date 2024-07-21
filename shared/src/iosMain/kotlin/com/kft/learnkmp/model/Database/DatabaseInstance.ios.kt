package com.kft.learnkmp.model.Database
import com.kft.learnkmp.model.Database.AppDatabase
import com.kft.learnkmp.model.Database.DatabaseInstance

class IosDatabaseInstance() : DatabaseInstance {
    override val getDatabaseInstance: AppDatabase? = null
}
actual fun getDatabaseInstance(): DatabaseInstance = IosDatabaseInstance()