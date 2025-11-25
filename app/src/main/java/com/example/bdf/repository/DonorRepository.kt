package com.example.bdf.repository

import com.example.bdf.api.ApiClient
import com.example.bdf.model.Donor
import retrofit2.Response

class DonorRepository {
    private val apiService = ApiClient.apiService

    suspend fun getDonors(): Response<List<Donor>> {
        return apiService.getDonors()
    }

    suspend fun filterDonors(bloodGroup: String): Response<List<Donor>> {
        return apiService.filterDonors(bloodGroup)
    }

    suspend fun createDonor(donor: Donor): Response<Donor> {
        return apiService.createDonor(donor)
    }
}
