package com.chow.alebeer.api

import com.chow.alebeer.model.BaseResponse
import com.chow.alebeer.model.BeerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {
    @GET
    suspend fun getBeers(@Url url: String): Response<BaseResponse<List<BeerResponse>>>
}