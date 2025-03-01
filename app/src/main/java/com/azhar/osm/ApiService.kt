package com.azhar.osm

import com.azhar.osm.ModelJembatanTrialItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v1/jembatan") // Replace with your actual endpoint
    fun getJembatanData(): Call<List<ModelJembatanTrialItem>>
}