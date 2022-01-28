package com.ros.stjohnshhunt.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.data.RealtyRepository
import com.ros.stjohnshhunt.data.SearchConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PropertySearchViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: RealtyRepository
) : ViewModel() {

    val searches: LiveData<List<SearchConfig>> = repository.getSearches().asLiveData()

    fun saveSearch(name: String, bedRange: String, bathRange: String, minPrice: String, maxPrice: String) {
        if (name.isEmpty() || bedRange.isEmpty() || bathRange.isEmpty() || minPrice.isEmpty() || maxPrice.isEmpty()) {
            Timber.d("saveSearch - field blank so not saving")
            return
        }

        viewModelScope.launch(context = Dispatchers.IO) {
            repository.saveSearch(
                SearchConfig(
                    name = name,
                    createdAtMillis = SystemClock.currentThreadTimeMillis(),
                    bedRange = bedRange,
                    bathRange = bathRange,
                    priceMin = minPrice,
                    priceMax = maxPrice)
            )
        }
    }
}
