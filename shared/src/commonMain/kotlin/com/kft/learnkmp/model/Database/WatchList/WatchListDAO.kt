package com.kft.learnkmp.model.Database.WatchList

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kft.learnkmp.Utils.Location
import kotlinx.coroutines.flow.Flow


@Dao
interface WatchListDAO {

    @Insert
    suspend fun insertIntoWatchList(watchListDTO: WatchListDTO)

    @Query("SELECT * FROM WatchList")
    fun getAllWatchList() : Flow<List<WatchListDTO>>

    @Delete
    suspend fun deleteFromWatchList(watchListDTO: WatchListDTO)

    @Query("SELECT * FROM WatchList WHERE lat = :lat AND lon = :lon")
    fun getCurrentWatchListState(lat : Double, lon : Double) : Flow<WatchListDTO?>


}