package com.team.nineg.ui.creation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.nineg.base.UiState
import com.team.nineg.data.db.GoodyRepository
import com.team.nineg.data.db.domain.Goody
import com.team.nineg.retrofit.ApiResult
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
        title: String,
        content: String,
        dueDate: String,
        image: MultipartBody.Part
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                repository.registerGoody(title, content, dueDate, image)
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

    fun requestGoodyList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getGoodyList()

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

    fun updateGoody(
        goodyId: String,
        title: String?,
        content: String?,
        dueDate: String?,
        image: MultipartBody.Part?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                repository.updateGoody(goodyId, title, content, dueDate, image)
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
}