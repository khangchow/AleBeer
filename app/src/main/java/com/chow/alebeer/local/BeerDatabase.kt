package com.chow.alebeer.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chow.alebeer.model.BeerEntity

@Database(entities = [BeerEntity::class], version = 1)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
}