package com.example.meditrack.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meditrack.databinding.ItemPasienBinding
import com.example.meditrack.model.PasienItem

class PasienAdapter(private val listPasien: List<PasienItem>) :
    RecyclerView.Adapter<PasienAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPasienBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPasienBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pasien = listPasien[position]
        holder.binding.tvNamaPasien.text = pasien.nama
        holder.binding.tvAlamat.text = pasien.alamat

    }

    override fun getItemCount(): Int {
        return listPasien.size // Pastikan ini bukan return 0
    }
}