package com.azhar.osm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.azhar.osm.ModelJembatanTrialItem
import com.azhar.osm.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JembatanRepository {
    private val jembatanData = MutableLiveData<List<ModelJembatanTrialItem>>()

    fun fetchJembatanData(): LiveData<List<ModelJembatanTrialItem>> {
        RetrofitClient.instance.getJembatanData().enqueue(object : Callback<List<ModelJembatanTrialItem>> {
            override fun onResponse(call: Call<List<ModelJembatanTrialItem>>, response: Response<List<ModelJembatanTrialItem>>) {
                if (response.isSuccessful) {
                    jembatanData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<ModelJembatanTrialItem>>, t: Throwable) {
                // Handle error
            }
        })
        return jembatanData
    }
}