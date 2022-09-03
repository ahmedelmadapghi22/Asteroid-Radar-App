package com.udacity.asteroidradar
import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.database.asteroid.AsteroidDatabase
import com.udacity.asteroidradar.database.pictureofday.PictureOfDayDatabase
import com.udacity.asteroidradar.workmanager.RefreshWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayInit()
        PictureOfDayDatabase.getInstance(this)
        AsteroidDatabase.getInstance(this)

    }
    private fun delayInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(
                RefreshWorker.WORKER_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
            )
    }



}