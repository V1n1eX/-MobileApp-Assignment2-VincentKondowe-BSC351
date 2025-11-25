package com.example.bdf

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bdf.adapter.DonorAdapter
import com.example.bdf.databinding.ActivityRecipientSearchBinding
import com.example.bdf.viewmodel.RecipientViewModel

class RecipientSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipientSearchBinding
    private val viewModel: RecipientViewModel by viewModels()
    private lateinit var adapter: DonorAdapter
    private var selectedBloodGroup = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipientSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBloodGroupSpinner()
        setupRecyclerView()

        binding.btnSearch.setOnClickListener {
            if (selectedBloodGroup.isNotEmpty()) {
                viewModel.searchDonors(selectedBloodGroup)
            } else {
                Toast.makeText(this, "Please select a blood group", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.donors.observe(this, Observer { donors ->
            adapter.setDonors(donors)
            if (donors.isNullOrEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.recyclerViewDonors.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.recyclerViewDonors.visibility = View.VISIBLE
            }
        })

        viewModel.error.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer { loading ->
            binding.progressLoading.visibility = if (loading) View.VISIBLE else View.GONE
        })
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

    private fun setupRecyclerView() {
        adapter = DonorAdapter()
        binding.recyclerViewDonors.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewDonors.adapter = adapter
    }
}
