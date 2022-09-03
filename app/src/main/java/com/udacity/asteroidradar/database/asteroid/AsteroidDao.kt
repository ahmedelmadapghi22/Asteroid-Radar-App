package com.udacity.asteroidradar.database.asteroid

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.Asteroid

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroids: List<Asteroid>): List<Long>


    @Query("SELECT * FROM Asteroid ORDER BY date(closeApproachDate) ASC")
    fun getAll(): List<Asteroid>

    @Query("SELECT * FROM Asteroid WHERE closeApproachDate=:currentDate")
    fun getAsteroidOfToday(currentDate: String): List<Asteroid>

    @Query("SELECT * FROM Asteroid WHERE closeApproachDate > datetime('now', 'start of day', 'weekday 6', '-7 day') ORDER BY closeApproachDate ASC")
    fun getAsteroidOfWeek(): List<Asteroid>


}