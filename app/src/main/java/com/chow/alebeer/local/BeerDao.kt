package com.chow.alebeer.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chow.alebeer.model.BeerEntity

@Dao
interface BeerDao {
    @Query("SELECT * FROM beer WHERE id = :id")
    suspend fun findBeerById(id: Int): BeerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(beerEntity: BeerEntity): Long

    @Query("SELECT * FROM beer ORDER BY createdAt asc")
    fun getFavorites(): LiveData<List<BeerEntity>>

    @Delete
    suspend fun delete(beerEntity: BeerEntity): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(beerEntity: BeerEntity): Int
}