package com.example.nineg.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineg.data.db.remote.UserRemoteDataSource
import com.example.nineg.util.MutableSingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : ViewModel() {

    private var isWaiting = false
    private var finishNetwork = false

    private var _isNetworkSuccess = MutableSingleLiveData<Boolean>()
    val isNetworkError: MutableSingleLiveData<Boolean>
        get() = _isNetworkSuccess

    init {
        viewModelScope.launch {
            delay(DELAY_SPLASH_TIME)
            isWaiting = true
        }
    }

    fun isUReady(): Boolean {
        return isWaiting && finishNetwork
    }

    fun initUserData(deviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSuccessNetwork = searchUser(deviceId).await() || createUser(deviceId).await()

            finishNetwork = true
            if (isSuccessNetwork) {
                _isNetworkSuccess.postValue(true)
            } else {
                _isNetworkSuccess.postValue(false)
            }
        }
    }

    private fun searchUser(deviceId: String): Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.IO) {
            try {
                val response = userRemoteDataSource.searchUser(deviceId)
                if (response.isSuccessful) {
                    true
                } else {
                    val error = response.errorBody()?.string()
                    Log.e(TAG, "searchUser: $error")
                    false
                }
            } catch (e: ConnectException) {
                e.printStackTrace()
                false
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    private fun createUser(deviceId: String): Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.IO) {
            try {
                val response = userRemoteDataSource.createUser(deviceId)
                if (response.isSuccessful) {
                    true
                } else {
                    val error = response.errorBody()?.string()
                    false
                }
            } catch (e: ConnectException) {
                false
            } catch (e: Exception) {
                false
            }

        }
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val DELAY_SPLASH_TIME = 2000L
    }
}