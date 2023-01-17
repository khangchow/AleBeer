package com.chow.alebeer.ui.main_screen

import com.chow.alebeer.api.Api
import com.chow.alebeer.local.BeerDao
import com.chow.alebeer.model.BeerEntity
import com.chow.alebeer.model.BeerModel

class MainRepository(private val api: Api, private val local: BeerDao) {
    suspend fun getBeers() = api.getBeers("api/api-testing/sample-data?page=1&limit=20")

    suspend fun getBeerById(id: Int) = local.findBeerByName(id)

    suspend fun saveBeer(beerEntity: BeerEntity) = local.insert(beerEntity)

    fun getFavorites() = local.getFavorites()

    suspend fun updateBeer(beerEntity: BeerEntity) = local.update(beerEntity)

    suspend fun deleteBeer(beerEntity: BeerEntity) = local.delete(beerEntity)
}