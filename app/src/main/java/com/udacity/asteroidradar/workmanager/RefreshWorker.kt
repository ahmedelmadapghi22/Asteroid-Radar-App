package com.udacity.asteroidradar.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.asteroid.AsteroidDatabase
import com.udacity.asteroidradar.repo.RepoAsteroid
import com.udacity.asteroidradar.repo.RepoPictureOfDay

class RefreshWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    companion object {
        const val WORKER_NAME = "AsteroidWorker"
    }

    private val pictureRepo = RepoPictureOfDay()
    private val asteroidRepo = RepoAsteroid()
    override suspend fun doWork(): Result {
        return try {
            pictureRepo.refreshPicture()
            asteroidRepo.refreshAsteroid()
            Result.success()
        }catch (ex : Exception){
            Result.retry()
        }


    }


}
