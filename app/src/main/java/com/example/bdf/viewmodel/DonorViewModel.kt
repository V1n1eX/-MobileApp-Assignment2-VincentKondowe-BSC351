package com.example.bdf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bdf.model.Donor
import com.example.bdf.repository.DonorRepository
import com.example.bdf.repository.FirebaseRepository
import kotlinx.coroutines.launch

class DonorViewModel : ViewModel() {
    private val repository = DonorRepository()
    // Minimal toggle to use Firebase instead of the existing API client.
    // Set to `true` to use Firestore-backed `FirebaseRepository` (requires google-services.json).
    // Using Firestore (no auth required).
    private val USE_FIREBASE = true
    private val firebaseRepository = FirebaseRepository()

    private val _registrationResult = MutableLiveData<String>()
    val registrationResult: LiveData<String> get() = _registrationResult
    
    private val _registrationError = MutableLiveData<String?>()
    val registrationError: LiveData<String?> get() = _registrationError

    fun registerDonor(donor: Donor) {
        viewModelScope.launch {
            try {
                if (USE_FIREBASE) {
                    val ok = firebaseRepository.registerDonor(donor)
                    _registrationResult.value = if (ok) "success" else "error"
                    if (!ok) _registrationError.value = "Failed to write to Firestore"
                } else {
                    val response = repository.createDonor(donor)
                    if (response.isSuccessful) {
                        _registrationResult.value = "success"
                    } else {
                        _registrationResult.value = "error"
                        _registrationError.value = "Server returned ${response.code()}"
                    }
                }
            } catch (e: Exception) {
                _registrationResult.value = "error"
                _registrationError.value = e.message ?: "Unknown error"
            }
        }
    }
}
