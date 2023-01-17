package com.chow.alebeer.di

import com.chow.alebeer.other.Constants
import com.chow.alebeer.api.Api
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single { provideRetrofit() }
    single { provideApiService(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideApiService(retrofit: Retrofit): Api {
    return retrofit.create(Api::class.java)
}


