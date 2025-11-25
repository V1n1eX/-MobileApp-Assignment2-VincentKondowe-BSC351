package com.example.bdf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bdf.model.Donor
import com.example.bdf.repository.DonorRepository
import com.example.bdf.repository.FirebaseRepository
import kotlinx.coroutines.launch

class RecipientViewModel : ViewModel() {
    private val repository = DonorRepository()
    private val firebaseRepository = FirebaseRepository()

    // Toggle to use Firestore instead of Retrofit backend.
    // Using Firestore (no auth required).
    private val USE_FIREBASE = true

    private val _donors = MutableLiveData<List<Donor>>()
    val donors: LiveData<List<Donor>> get() = _donors

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    fun searchDonors(bloodGroup: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                if (USE_FIREBASE) {
                    val donorsList = firebaseRepository.searchDonorsByBloodGroup(bloodGroup)
                    _donors.value = donorsList
                } else {
                    val response = repository.filterDonors(bloodGroup)
                    if (response.isSuccessful) {
                        _donors.value = response.body() ?: emptyList()
                    } else {
                        _error.value = "Error fetching donors"
                    }
                }
            } catch (e: Exception) {
                _error.value = "Error fetching donors"
            } finally {
                _loading.value = false
            }
        }
    }
}
