package com.team.nineg.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.nineg.data.db.GoodyRepository
import com.team.nineg.retrofit.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(private val repository: GoodyRepository) : ViewModel() {
    private val _deleteGoody = MutableLiveData<Boolean>()
    val deleteGoody: LiveData<Boolean> get() = _deleteGoody

    fun deleteGoody(goodyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.removeGoody(goodyId)
            _deleteGoody.postValue(result is ApiResult.Success)
        }
    }
}