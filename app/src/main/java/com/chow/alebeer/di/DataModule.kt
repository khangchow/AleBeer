package com.chow.alebeer.di

import android.content.Context
import androidx.room.Room
import com.chow.alebeer.local.BeerDatabase
import com.chow.alebeer.other.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single { provideBeerDatabase(androidApplication()) }
    single { provideBeerDao(get()) }
}

fun provideBeerDatabase(context: Context) = Room.databaseBuilder(
    context,
    BeerDatabase::class.java,
    Constants.LOCAL_BEER_DB
).build()

fun provideBeerDao(beerDatabase: BeerDatabase) = beerDatabase.beerDao()