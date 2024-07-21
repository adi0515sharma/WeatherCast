package com.kft.learnkmp.Utils


sealed class ResponseHandler<out T> {
    data class Success<out T>(val data: T) : ResponseHandler<T>()
    data class Error(val message: String?, val throwable: Throwable? = null) : ResponseHandler<Nothing>()
    object Loading : ResponseHandler<Nothing>()
}
