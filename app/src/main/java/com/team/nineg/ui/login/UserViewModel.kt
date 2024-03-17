package com.team.nineg.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.nineg.base.UiState
import com.team.nineg.data.db.UserRepository
import com.team.nineg.data.db.domain.User
import com.team.nineg.retrofit.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _user = MutableLiveData<User?>(null)
    val user: LiveData<User?> get() = _user
    private val _state = MutableLiveData<UiState<Unit>>()
    val state: LiveData<UiState<Unit>> get() = _state

    fun login(accessToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepository.login(accessToken)

            when (result) {
                is ApiResult.Success -> {
                    _user.postValue(result.value)
                    _state.postValue(UiState.Success(Unit))
                }
                is ApiResult.Error -> {
                    _user.postValue(null)
                    _state.postValue(UiState.Error(result.code, result.exception))
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.logout()
            _user.postValue(null)
        }
    }

    fun revoke() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepository.revoke(user.value?.deviceId!!)

            when (result) {
                is ApiResult.Success -> {
                    _user.postValue(null)
                }
                is ApiResult.Error -> {
                    // error message
                }
            }
        }
    }
}