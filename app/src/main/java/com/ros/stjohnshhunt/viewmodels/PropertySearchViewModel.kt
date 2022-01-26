package com.ros.stjohnshhunt.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ros.stjohnshhunt.data.RealtyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class PropertySearchViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: RealtyRepository
) : ViewModel() {

}