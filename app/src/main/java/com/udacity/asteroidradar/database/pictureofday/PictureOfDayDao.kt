package com.udacity.asteroidradar.database.pictureofday

import androidx.room.*
import com.udacity.asteroidradar.model.PictureOfDay

@Dao
interface PictureOfDayDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(pic: PictureOfDay)

    @Query("SELECT * FROM pictureofday")
    fun getPictureOfTheDay(): PictureOfDay

}
