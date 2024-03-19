package com.team.nineg.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.nineg.data.db.UserRepository
import com.team.nineg.data.db.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _profile = MutableLiveData<User>()
    val profile: LiveData<User> get() = _profile

    fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUser()?.let { _profile.postValue(it) }
        }
    }
}