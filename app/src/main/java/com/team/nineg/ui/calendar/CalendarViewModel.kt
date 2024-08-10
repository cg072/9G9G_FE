package com.team.nineg.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.nineg.data.db.GoodyRepository
import com.team.nineg.data.db.domain.Goody
import com.team.nineg.retrofit.ApiResult
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

    fun requestGoodyList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getGoodyList()

            when (result) {
                is ApiResult.Success -> {
                    _goodyMap.postValue(result.value.associateBy { it.dueDate })
                }
                is ApiResult.Error -> {
                    result.exception?.printStackTrace()
                }
            }
        }
    }
}