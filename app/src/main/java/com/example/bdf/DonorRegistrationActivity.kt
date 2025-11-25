package com.example.bdf

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bdf.databinding.ActivityDonorRegistrationBinding
import com.example.bdf.model.Donor
import com.example.bdf.viewmodel.DonorViewModel

class DonorRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonorRegistrationBinding
    private val viewModel: DonorViewModel by viewModels()
    private var selectedBloodGroup = ""
    private var selectedCity = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonorRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBloodGroupSpinner()

        binding.btnSubmit.setOnClickListener {
            // Prevent duplicate taps
            binding.btnSubmit.isEnabled = false
            registerDonor()
        }
        viewModel.registrationResult.observe(this, Observer { result ->
            when (result) {
                "success" -> {
                    // Show modal confirmation so user definitely sees it
                    AlertDialog.Builder(this)
                        .setMessage(getString(R.string.donor_registered_successfully))
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            finish()
                        }
                        .setCancelable(false)
                        .show()
                }
                "error" -> {
                    // If error, read more detailed message from registrationError
                    val err = viewModel.registrationError.value ?: getString(R.string.error_registration)
                    AlertDialog.Builder(this)
                        .setTitle("Registration failed")
                        .setMessage(err)
                        .setPositiveButton(android.R.string.ok, null)
                        .show()
                    binding.btnSubmit.isEnabled = true
                }
            }
        })

        viewModel.registrationError.observe(this, Observer { err ->
            // If an error message appears separately, show it (guard against duplicate dialogs)
            if (!err.isNullOrEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Registration error")
                    .setMessage(err)
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                binding.btnSubmit.isEnabled = true
            }
        })

        setupCitySpinner()
    }

    private fun setupBloodGroupSpinner() {
        val bloodGroups = arrayOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodGroups)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerBloodGroup.adapter = adapter

        binding.spinnerBloodGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedBloodGroup = bloodGroups[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupCitySpinner() {
        val cities = arrayOf("Mzuzu", "Lilongwe", "Blantyre", "Zomba")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCity.adapter = adapter

        binding.spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCity = cities[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun registerDonor() {
        val fullName = binding.etFullName.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val city = selectedCity
        val localArea = binding.etLocalArea.text.toString().trim()
        val available = binding.switchAvailability.isChecked

        if (fullName.isEmpty() || phone.isEmpty() || city.isEmpty() || selectedBloodGroup.isEmpty() || localArea.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            binding.btnSubmit.isEnabled = true
            return
        }

        val donor = Donor(
            id = 0, // Will be set by server
            full_name = fullName,
            phone = phone,
            city = "$city - $localArea",
            blood_group = selectedBloodGroup,
            available = available,
            last_donation = null
        )

        viewModel.registerDonor(donor)
    }
}
