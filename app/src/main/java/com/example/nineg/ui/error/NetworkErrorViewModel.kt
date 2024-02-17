package com.example.nineg.ui.error

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nineg.util.MutableSingleLiveData
import com.example.nineg.util.SingleLiveData

class NetworkErrorViewModel : ViewModel() {
    private val _retryConnection = MutableSingleLiveData<Any>()
    val retryConnection: SingleLiveData<Any>
        get() = _retryConnection

    fun retryConnection() {
        Log.d(TAG, "retryConnection: ")
        _retryConnection.postValue(Any())
    }

    companion object {
        private const val TAG = "NetworkErrorViewModel"
    }
}