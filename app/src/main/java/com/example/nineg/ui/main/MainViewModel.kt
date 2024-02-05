package com.example.nineg.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var isReady = false
        private set

    init {
        viewModelScope.launch {
            delay(DELAY_SPLASH_TIME)
            isReady = true
        }
    }

    companion object {
        private const val DELAY_SPLASH_TIME = 2000L
    }
}