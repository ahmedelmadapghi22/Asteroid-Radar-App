package com.udacity.asteroidradar.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "pictureOfDay")
data class PictureOfDay(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val copyright: String = "",
    val date: String = "",
    val explanation: String = "",
    val hdurl: String = "",
    val media_type: String = "",
    val service_version: String = "",
    val title: String = "",
    val url: String = "",

)




