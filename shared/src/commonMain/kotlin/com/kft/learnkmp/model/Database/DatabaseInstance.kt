package com.kft.learnkmp.model.Database



interface DatabaseInstance {
    val getDatabaseInstance : AppDatabase?
}

expect fun getDatabaseInstance(): DatabaseInstance