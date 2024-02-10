package com.example.nineg.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineg.data.db.remote.UserRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : ViewModel() {

    private var isWaiting = false
    private var isUserDataReady = false

    init {
        viewModelScope.launch {
            delay(DELAY_SPLASH_TIME)
            isWaiting = true
        }
    }

    fun isUReady(): Boolean {
        return isWaiting && isUserDataReady
    }

    fun initUserData(deviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isUserDataReady = searchUser(deviceId).await() || createUser(deviceId).await()

            if (!isUserDataReady) {
                // 에러 메시지 및 앱 종료(?)
            }
        }
    }

    private fun searchUser(deviceId: String): Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.IO) {
            val response = userRemoteDataSource.searchUser(deviceId)
            if (response.isSuccessful) {
                Log.d(TAG, "searchUser ${response.body()?.deviceId} ${response.body()?.createdAt}")
                true
            } else {
                val error = response.errorBody()?.string()
                Log.e(TAG, "searchUser: $error")
                false
            }
        }
    }

    private fun createUser(deviceId: String): Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.IO) {
            Log.d(TAG, "createUser: ")
            val response = userRemoteDataSource.createUser(deviceId, "", "0", "")
            if (response.isSuccessful) {
                Log.d(TAG, "createUser ${response.body()?.deviceId} ${response.body()?.createdAt}")
                true
            } else {
                val error = response.errorBody()?.string()
                Log.e(TAG, "createUser: $error")
                false
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val DELAY_SPLASH_TIME = 2000L
    }
}