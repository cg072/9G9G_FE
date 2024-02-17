package com.example.nineg.base

sealed class UiState<out T> {
    object Uninitialized: UiState<Nothing>()
    object Loading: UiState<Nothing>()
    object Empty: UiState<Nothing>()
    data class Success<T>(val data: T, var isRefresh: Boolean = false, var isMore: Boolean = false): UiState<T>()
    data class Error(val code: Int? = null, val exception: Throwable? = null): UiState<Nothing>()
}
