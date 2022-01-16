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
class PropertiesViewModel @Inject constructor(
    private val repository: RealtyRepository
) : ViewModel() {

    val houses: LiveData<List<House>> = repository.getHouses().asLiveData()

    init {
        refreshProperties()
    }

    private fun refreshProperties() {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.getSearchResidential(
                bedRange = "2-3",
                bathRange = "1-2",
                minPrice = "180000",
                maxPrice = "400000"
            )
        }
    }
}
