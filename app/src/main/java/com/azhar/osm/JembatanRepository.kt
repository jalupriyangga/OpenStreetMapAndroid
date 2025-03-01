package com.azhar.osm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JembatanRepository {
    private val jembatanData = MutableLiveData<List<ModelJembatanTrialItem>>()

    fun fetchJembatanData(): LiveData<List<ModelJembatanTrialItem>> {
        RetrofitClient.instance.getJembatanData().enqueue(object : Callback<List<ModelJembatanTrialItem>> {
            override fun onResponse(call: Call<List<ModelJembatanTrialItem>>, response: Response<List<ModelJembatanTrialItem>>) {
                if (response.isSuccessful) {
                    Log.d("JembatanRepository", "Raw JSON: ${response.body()?.toString()}")
                    jembatanData.postValue(response.body())
                } else {
                    Log.e("JembatanRepository", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<ModelJembatanTrialItem>>, t: Throwable) {
                // Handle error
                Log.e("JembatanRepository", "Error fetching jembatan data: ${t.message}")
            }
        })
        return jembatanData
    }
}