package com.kft.learnkmp.model.Database.SaveList

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface SavedListDAO {

    @Insert
    suspend fun insertIntoSavedList(SavedListDTO: SavedListDTO)

    @Query("SELECT * FROM SavedList")
    fun getAllSavedList() : Flow<List<SavedListDTO>>

    @Delete
    suspend fun deleteFromSavedList(SavedListDTO: SavedListDTO)

    @Query("DELETE FROM SavedList WHERE lat = :lat AND lon = :lon")
    suspend fun deleteFromSavedList(lat : Double, lon : Double)

}