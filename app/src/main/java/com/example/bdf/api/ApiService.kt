package com.example.bdf.api

import com.example.bdf.model.Donor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("donors/")
    suspend fun getDonors(): Response<List<Donor>>

    @GET("donors/")
    suspend fun filterDonors(@Query("blood_group") bloodGroup: String): Response<List<Donor>>

    @POST("donors/")
    suspend fun createDonor(@Body donor: Donor): Response<Donor>
}
