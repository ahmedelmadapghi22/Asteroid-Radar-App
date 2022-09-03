package com.udacity.asteroidradar.repo

import android.util.Log
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidRetrofit
import com.udacity.asteroidradar.database.pictureofday.PictureOfDayDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RepoPictureOfDay {

    suspend fun refreshPicture(): PictureOfDay {
        val retrofit = AsteroidRetrofit()
        var pictureData: PictureOfDay
        try {
            val remotePic = retrofit.getRetroService()
                .getPictureOfDay() // APIKEY Inserted as default from ServiceInterface
            if (remotePic.media_type == "image") {
                PictureOfDayDatabase.instance?.pictureOfDayDao()?.insertPicture(remotePic)

            } else {
                return PictureOfDayDatabase.instance?.pictureOfDayDao()?.getPictureOfTheDay()!!
            }

        } catch (ex: Exception) {
            Log.d("dapgoo", ex.message.toString())
            return PictureOfDayDatabase.instance?.pictureOfDayDao()?.getPictureOfTheDay()!!
        }
        withContext(Dispatchers.IO) {
            pictureData = PictureOfDayDatabase.instance?.pictureOfDayDao()?.getPictureOfTheDay()!!
        }

        return pictureData
    }

    suspend fun getPicture(): PictureOfDay {
        var pictureData: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureData = if (PictureOfDayDatabase.instance?.pictureOfDayDao()
                    ?.getPictureOfTheDay() == null
            ) {
                refreshPicture()
            } else {
                PictureOfDayDatabase.instance?.pictureOfDayDao()?.getPictureOfTheDay()!!

            }
        }
        return pictureData
    }
}