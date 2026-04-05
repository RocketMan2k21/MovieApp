package com.romahduda.movies30.util

sealed class DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>()
    data class Error(val message: String, val exception: Exception? = null) : DataResult<Nothing>()

    fun isError() = this is Error
    fun isSuccess() = this is Success
}