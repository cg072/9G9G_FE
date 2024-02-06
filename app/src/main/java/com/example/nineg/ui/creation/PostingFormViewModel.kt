package com.example.nineg.ui.creation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineg.data.db.GoodyRepository
import com.example.nineg.retrofit.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class PostingFormViewModel @Inject constructor(private val repository: GoodyRepository) :
    ViewModel() {

    fun registerGoody2(
        deviceId: String,
        missionTitle: String,
        title: String,
        content: String,
        photoUrl: String,
        image: MultipartBody.Part
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                repository.registerGoody(deviceId, missionTitle, title, content, photoUrl, image)
            when (result) {
                is ApiResult.Success -> {
                    Log.d("kch", "kch success ${result.value.title}")
                }
                is ApiResult.Error -> {
                    result.exception?.printStackTrace()
                }
            }
        }
    }
}