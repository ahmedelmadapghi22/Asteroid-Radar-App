package com.udacity.asteroidradar.database.asteroid

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.model.Asteroid

private const val DB_NAME = "asteroid-db"

@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDao

    companion object {
        var instance: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            if (instance == null) {
                instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        DB_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}