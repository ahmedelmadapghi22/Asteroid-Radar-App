package com.udacity.asteroidradar.database.pictureofday

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.model.PictureOfDay

private const val DB_NAME = "pictureOfDay-db"

@Database(entities = [PictureOfDay::class], version = 2)
abstract class PictureOfDayDatabase : RoomDatabase() {
    abstract fun pictureOfDayDao(): PictureOfDayDao

    companion object {
        var instance: PictureOfDayDatabase? = null

        fun getInstance(context: Context): PictureOfDayDatabase {
            if (instance == null) {
                instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        PictureOfDayDatabase::class.java,
                        DB_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}