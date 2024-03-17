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
    private val _state = MutableLiveData<UiState<User>>()
    val state: LiveData<UiState<User>> get() = _state

    fun success() {
        _user.value = User("c2914b032aba411c", "1", 10, "male")
    }

    fun initUserData(deviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepository.loginUser(deviceId)

            when (result) {
                is ApiResult.Success -> {
                    _user.postValue(result.value)
                    _state.postValue(UiState.Success(result.value))
                }
                is ApiResult.Error -> {
                    _state.postValue(UiState.Error(result.code, result.exception))
                }
            }
        }
    }

    fun logout() {
        _user.value = null
    }

    fun revoke() {
        _user.value = null
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val DELAY_SPLASH_TIME = 2000L
    }
}