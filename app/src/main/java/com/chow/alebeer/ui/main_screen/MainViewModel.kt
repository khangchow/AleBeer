package com.chow.alebeer.ui.main_screen

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chow.alebeer.model.*
import com.chow.alebeer.other.Constants
import com.chow.alebeer.other.Event
import com.chow.alebeer.other.FileUtils
import com.chow.alebeer.other.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {
    private val _getBeersStatus = MutableLiveData<Resource<List<BeerModel>>>()
    val getBeersStatus = _getBeersStatus
    private val _saveBeersStatus = MutableLiveData<Event<Resource<List<BeerModel>>>>()
    val saveBeersStatus = _saveBeersStatus
    private lateinit var list: List<BeerModel>
    private var isBeerDeletedLately = false
    private var isBeerUpdatedLately = false
    private var isViewModelCreatedFirstTime = true

    fun getFavorites() = mainRepository.getFavorites()

    fun getBeers() {
        viewModelScope.launch {
            replaceBeerWithLocalDataIfExistedAfterDeletingOrLoadingNewDataFromServer()
        }
    }

    fun saveBeer(index: Int, beerModel: BeerModel, bitmap: Bitmap?) {
        if (beerModel.note.isBlank()) return
        var currentBeer = beerModel
        list = list.toMutableList().apply {
            removeAt(index)
            add(index, currentBeer.copy(isSaving = true))
            toList()
        }
        _saveBeersStatus.postValue(Event(Resource.Success(list)))
        viewModelScope.launch {
            bitmap?.let {
                FileUtils.saveToInternalStorage(it)?.let { localImagePath ->
                    currentBeer = currentBeer.copy(localImagePath = localImagePath)
                }
            }
            (mainRepository.saveBeer(currentBeer.toBeerEntity()).toInt() - 1).let {
                if (it >= 0) {
                    list = list.toMutableList().apply {
                        removeAt(it)
                        add(it, currentBeer.copy(isSaving = false))
                        toList()
                    }
                    _saveBeersStatus.postValue(Event(Resource.Success(list)))
                }
            }
        }
    }

    fun deleteBeer(beerEntity: BeerEntity) {
        viewModelScope.launch {
            if ((mainRepository.deleteBeer(beerEntity)) == 1) isBeerDeletedLately = true
        }
    }

    fun updateBeer(beerEntity: BeerEntity) {
        if (beerEntity.note.isBlank()) deleteBeer(beerEntity)
        viewModelScope.launch {
            if ((mainRepository.updateBeer(beerEntity)) == 1) isBeerUpdatedLately = true
        }
    }

    fun onBeerUpdated() {
        if (isViewModelCreatedFirstTime.not() && (isBeerUpdatedLately || isBeerDeletedLately)) {
            if (isBeerDeletedLately) replaceBeerWithLocalDataIfExistedAfterDeletingOrLoadingNewDataFromServer()
            else replaceBeerWithLocalDataIfExistedAfterUpdating()
        } else isViewModelCreatedFirstTime = false
    }

    private fun replaceBeerWithLocalDataIfExistedAfterUpdating() {
        viewModelScope.launch {
            list = list.map { beer ->
                mainRepository.getBeerById(beer.id)?.toBeerModel()
                    ?.copy(image = beer.image)
                    ?: beer
            }
            isBeerUpdatedLately = false
            _getBeersStatus.postValue(
                Resource.Success(
                    list
                )
            )
        }
    }

    private fun replaceBeerWithLocalDataIfExistedAfterDeletingOrLoadingNewDataFromServer() {
        Log.d("CHOTAOTEST", "delete or load: ")
        viewModelScope.launch {
            mainRepository.getBeers().run {
                if (isSuccessful) {
                    body()?.let {
                        if (it.status == Constants.RESPONSE_STATUS_OK) {
                            it.data.toBeerList().map { beer ->
                                Log.d("CHOTAOTEST", "deleted: ${beer.name} ${mainRepository.getBeerById(beer.id)}")
                                mainRepository.getBeerById(beer.id)?.toBeerModel()
                                    ?.copy(image = beer.image)
                                    ?: beer
                            }.let { data ->
                                _getBeersStatus.postValue(Resource.Success(data))
                                list = data
                                isBeerDeletedLately = false
                            }
                        }
                    }
                }
            }
        }
    }
}