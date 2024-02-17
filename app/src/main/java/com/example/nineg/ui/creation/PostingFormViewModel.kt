package com.example.nineg.ui.creation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineg.base.UiState
import com.example.nineg.data.db.GoodyRepository
import com.example.nineg.data.db.domain.Goody
import com.example.nineg.retrofit.ApiResult
import com.example.nineg.ui.calendar.CalendarViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class PostingFormViewModel @Inject constructor(private val repository: GoodyRepository) :
    ViewModel() {

    private val _goodyState = MutableLiveData<UiState<Goody>>()
    val goodyState: LiveData<UiState<Goody>> get() = _goodyState

    private var dueDateSet: Set<String> = emptySet()

    fun getDueDateSet() = dueDateSet

    fun registerGoody(
        deviceId: String,
        title: String,
        content: String,
        dueDate: String,
        image: MultipartBody.Part
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                repository.registerGoody(deviceId, title, content, dueDate, image)
            when (result) {
                is ApiResult.Success -> {
                    _goodyState.postValue(UiState.Success(result.value))
                }
                is ApiResult.Error -> {
                    _goodyState.postValue(UiState.Error(result.code, result.exception))
                    result.exception?.printStackTrace()
                }
            }
        }
    }

    fun requestGoodyList(deviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getGoodyList(deviceId)

            when (result) {
                is ApiResult.Success -> {
                    dueDateSet = result.value.map { it.dueDate }.toSet()
                }
                is ApiResult.Error -> {
                    result.exception?.printStackTrace()
                }
            }
        }
    }
}