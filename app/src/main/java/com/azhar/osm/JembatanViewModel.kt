package com.azhar.osm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class JembatanViewModel : ViewModel() {
    private val repository = JembatanRepository()

    fun getJembatanData(): LiveData<List<ModelJembatanTrialItem>> {
        return repository.fetchJembatanData()
    }
}