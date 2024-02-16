package com.example.nineg.ui.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineg.data.db.GoodyRepository
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.retrofit.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val repository: GoodyRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "CalendarViewModel"
    }

    private val _goodyMap = MutableLiveData<Map<String, Goody>>()
    val goodyMap: LiveData<Map<String, Goody>> get() = _goodyMap

    fun requestGoodyList(deviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getGoodyList(deviceId)

            when (result) {
                is ApiResult.Success -> {
                    Log.d(TAG, "kch ApiResult.Success ${result}")
                    _goodyMap.postValue(result.value.associateBy { it.dueDate })
                }
                is ApiResult.Error -> {
                    result.exception?.printStackTrace()
                }
            }
        }
    }
}