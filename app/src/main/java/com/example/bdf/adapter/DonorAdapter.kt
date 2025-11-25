package com.example.bdf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bdf.R
import com.example.bdf.model.Donor

class DonorAdapter : RecyclerView.Adapter<DonorAdapter.DonorViewHolder>() {

    private var donors: List<Donor> = emptyList()

    fun setDonors(donors: List<Donor>) {
        this.donors = donors
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.donor_item, parent, false)
        return DonorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonorViewHolder, position: Int) {
        val donor = donors[position]
        holder.bind(donor)
    }

    override fun getItemCount(): Int = donors.size

    class DonorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvCity: TextView = itemView.findViewById(R.id.tvCity)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        private val tvBloodGroup: TextView = itemView.findViewById(R.id.tvBloodGroup)
        private val tvAvailability: TextView = itemView.findViewById(R.id.tvAvailability)

        fun bind(donor: Donor) {
            tvName.text = donor.full_name
            tvCity.text = donor.city
            tvPhone.text = donor.phone
            tvBloodGroup.text = donor.blood_group

            if (donor.available) {
                tvAvailability.text = "Available"
                tvAvailability.setBackgroundResource(R.color.green)
            } else {
                tvAvailability.text = "Not Available"
                tvAvailability.setBackgroundResource(R.color.red)
            }
        }
    }
}
