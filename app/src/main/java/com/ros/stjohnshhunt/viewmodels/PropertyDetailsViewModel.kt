package com.ros.stjohnshhunt.viewmodels

import androidx.lifecycle.*
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.data.RealtyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

@HiltViewModel
class PropertyDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RealtyRepository
) : ViewModel() {

    val houseId: String = savedStateHandle.get<String>(HOUSE_ID_SAVED_STATE_KEY)!!
    val house = repository.getHouse(houseId).asLiveData()

    companion object {
        private const val HOUSE_ID_SAVED_STATE_KEY = "houseId"
    }
}
