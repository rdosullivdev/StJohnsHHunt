package com.ros.stjohnshhunt.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.stjohnshhunt.DATE_FORMAT_1
import com.ros.stjohnshhunt.data.RealtyRepository
import com.ros.stjohnshhunt.extensions.asLiveData
import com.ros.stjohnshhunt.extensions.toReadableString
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DevViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var _recentSyncs = MutableLiveData<String>()
    val recentSyncs = _recentSyncs.asLiveData()

    init {
        _recentSyncs.postValue(
            sharedPreferences.getString(SYNC_RUNS_STATE_KEY, null) ?: "No Syncs Found"
        )
    }

    companion object {
        const val SYNC_RUNS_STATE_KEY = "SYNC_RUNS_STATE_KEY"
    }
}