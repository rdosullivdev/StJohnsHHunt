package com.ros.stjohnshhunt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _houses = MutableLiveData<List<House>>()
    val houses: LiveData<List<House>>
        get() = _houses

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
            )?.let {
                _houses.postValue(it)
            }
        }
    }
}
