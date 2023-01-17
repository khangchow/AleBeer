package com.chow.alebeer.model

data class BaseResponse<T>(
    val status: String,
    val data: T,
    val message: String,
    val loadMore: Boolean
)