package com.kft.learnkmp.model.Database.SaveList

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable

@Entity("SavedList")
data class SavedListDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name : String,
    val lat : Double,
    val lon : Double,
    val country : String,
    val state : String ? = null
)
