package com.example.bdf.model

data class Donor(
    val id: Int,
    val full_name: String,
    val phone: String,
    val city: String,
    val blood_group: String,
    val available: Boolean,
    val last_donation: String?
)
