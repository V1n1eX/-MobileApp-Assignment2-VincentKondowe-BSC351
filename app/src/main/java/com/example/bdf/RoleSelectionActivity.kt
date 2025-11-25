package com.example.bdf

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bdf.databinding.ActivityRoleSelectionBinding

class RoleSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoleSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDonor.setOnClickListener {
            startActivity(Intent(this, DonorRegistrationActivity::class.java))
        }

        binding.btnRecipient.setOnClickListener {
            startActivity(Intent(this, RecipientSearchActivity::class.java))
        }
    }
}
