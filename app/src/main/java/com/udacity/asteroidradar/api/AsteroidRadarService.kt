package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidRadarService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsAsync(
        @Query("api_key") api_key: String = Constants.API_KEY
    ): String

    @GET("planetary/apod")
   suspend fun getPictureOfDay(
        @Query("api_key") api_key: String = Constants.API_KEY
    ): PictureOfDay

}