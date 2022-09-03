package com.udacity.asteroidradar.ui.fragment.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.repo.RepoAsteroid
import com.udacity.asteroidradar.repo.RepoPictureOfDay
import kotlinx.coroutines.launch

enum class AsteroidStatus { LOADING, ERROR, DONE }
enum class PictureStatus { LOADING, ERROR, DONE }
class MainViewModel : ViewModel() {
    //init repos
    private val mRepoPictureOfDay = RepoPictureOfDay()
    private val mRepoAsteroid = RepoAsteroid()

    //live data for asteroid
    private val _asteroid = MutableLiveData<List<Asteroid>>()
    val asteroid: LiveData<List<Asteroid>> get() = _asteroid

    //live data for picture of day
    private val _picture = MutableLiveData<PictureOfDay>()
    val picture: LiveData<PictureOfDay> get() = _picture

    //asteroid to navigate
    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()

    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    //to handle status list of asteroid
    private val _status = MutableLiveData<AsteroidStatus>()
    val status: LiveData<AsteroidStatus>
        get() = _status

    //to handle status list of asteroid
    private val _statusPic = MutableLiveData<PictureStatus>()
    val statusPic: LiveData<PictureStatus>
        get() = _statusPic

    init {
        getAsteroid()
        onPictureOfTheDay()
    }

    // navigate
    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    // get list asteroid and handle the status
    private fun onPictureOfTheDay() {
        viewModelScope.launch {
            _statusPic.value = PictureStatus.LOADING
            try {
                _picture.postValue(mRepoPictureOfDay.getPicture())
                _statusPic.value = PictureStatus.DONE
            } catch (e: Exception) {
                _statusPic.value = PictureStatus.ERROR
                Log.d("dapgoo", "getMarsRealEstateProperties: ")
            }

        }
    }


    //get list asteroid and handle the status
     fun getAsteroid() {
        viewModelScope.launch {
            _status.value = AsteroidStatus.LOADING
            try {
                _asteroid.postValue(mRepoAsteroid.getAsteroid())
                _status.value = AsteroidStatus.DONE
            } catch (e: Exception) {
                _status.value = AsteroidStatus.ERROR
                Log.d("dapgoo", "getMarsRealEstateProperties: ")
            }

        }
    }

    //get list asteroid of day and handle the status
    fun getAsteroidOfDay(currentDay: String) {
        viewModelScope.launch {
            _status.value = AsteroidStatus.LOADING
            try {
                _asteroid.postValue(mRepoAsteroid.getAsteroidOfDay(currentDay))
                _status.value = AsteroidStatus.DONE
            } catch (e: Exception) {
                _status.value = AsteroidStatus.ERROR
                Log.d("dapgoo", "getMarsRealEstateProperties: ")
            }

        }
    }

    //get list asteroid of week and handle the status
    fun getAsteroidOfWeek() {
        viewModelScope.launch {
            _status.value = AsteroidStatus.LOADING
            try {
                _asteroid.postValue(mRepoAsteroid.getAsteroidOfWeek())
                _status.value = AsteroidStatus.DONE
            } catch (e: Exception) {
                _status.value = AsteroidStatus.ERROR
                Log.d("dapgoo", "getMarsRealEstateProperties: ")
            }

        }
    }
}