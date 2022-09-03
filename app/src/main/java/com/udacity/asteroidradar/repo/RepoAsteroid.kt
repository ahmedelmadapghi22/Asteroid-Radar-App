package com.udacity.asteroidradar.repo

import android.util.Log
import com.udacity.asteroidradar.api.AsteroidRetrofit
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.asteroid.AsteroidDatabase
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class RepoAsteroid() {
    private val doa = AsteroidDatabase.instance?.asteroidDao()

    suspend fun refreshAsteroid() {
        val retrofit = AsteroidRetrofit()
        try {
            val asteroids = retrofit.getRetroService().getAsteroidsAsync()
            doa?.insertAll(parseAsteroidsJsonResult(JSONObject(asteroids)))

        } catch (ex: Exception) {
            Log.d("dapgoo", ex.message.toString())
        }

    }


    suspend fun getAsteroid(): List<Asteroid> {
        var asteroidList: List<Asteroid>
        withContext(Dispatchers.IO) {
            val asteroid = doa?.getAll()
            try {
                asteroid?.let {
                    if (it.isEmpty()) {
                        refreshAsteroid()
                    }
                }
            } catch (ex: Exception) {
                Log.d("dapgoo", "getAsteroid:${ex.message}")
            }
            asteroidList = doa?.getAll()!!
        }
        return asteroidList
    }

    suspend fun getAsteroidOfDay(currentDay: String): List<Asteroid> {
        var asteroidList: List<Asteroid>
        withContext(Dispatchers.IO) {
            val asteroid = doa?.getAsteroidOfToday(currentDay)
            try {
                asteroid?.let {
                    if (it.isEmpty()) {
                        refreshAsteroid()
                    }
                }
            } catch (ex: Exception) {
                Log.d("dapgoo", "getAsteroid:${ex.message}")
            }
            asteroidList = doa?.getAsteroidOfToday(currentDay)!!
        }
        return asteroidList
    }

    suspend fun getAsteroidOfWeek(): List<Asteroid> {
        var asteroidList: List<Asteroid>
        withContext(Dispatchers.IO) {
            val asteroid = doa?.getAsteroidOfWeek()
            try {
                asteroid?.let {
                    if (it.isEmpty()) {
                        refreshAsteroid()
                    }
                }
            } catch (ex: Exception) {
                Log.d("dapgoo", "getAsteroid:${ex.message}")
            }
            asteroidList = doa?.getAsteroidOfWeek()!!
        }
        return asteroidList
    }


}