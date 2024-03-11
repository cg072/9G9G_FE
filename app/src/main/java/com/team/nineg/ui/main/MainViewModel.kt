package com.team.nineg.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _startNavShowCase = MutableLiveData<Any>()
    val startNavShowCase: LiveData<Any> = _startNavShowCase

    private val _refreshScreen = MutableLiveData<Unit>()
    val refreshScreen: LiveData<Unit> get() = _refreshScreen

    fun startTutorialNav() {
        _startNavShowCase.postValue(Any())
    }

    fun refreshScreen() {
        _refreshScreen.value = Unit
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}