package com.chow.alebeer.other

sealed class Resource<T> {
    class Success<T>(val data: T? = null) : Resource<T>()
    class Error<T>(val data: T? = null) : Resource<T>()
    class Loading<T>(val data: T? = null) : Resource<T>()
}