package com.romahduda.movies30.util

sealed interface UiState<out T> {
    object Idle : UiState<Nothing>
    object Loading: UiState<Nothing>
    data class Success<T>(val data: T): UiState<T>
    data class Error(val error: Throwable): UiState<Nothing>
}