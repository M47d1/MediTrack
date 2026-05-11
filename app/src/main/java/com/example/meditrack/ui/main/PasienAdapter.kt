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

        with(holder.binding) {
            // 1. Logika Inisial Nama (Ambil 1 huruf depan)
            val inisial = if (!pasien.nama.isNullOrEmpty()) {
                pasien.nama.trim().take(1).uppercase()
            } else {
                "?"
            }
            tvInisial.text = inisial

            // 2. Set Nama Pasien
            tvNamaPasien.text = pasien.nama

            // 3. Set Jenis Kelamin & Tanggal Lahir (Digabung agar rapi)
            val jk = pasien.jenisKelamin ?: "-"
            val tgl = pasien.tanggalLahir ?: "-"
            tvDetailInfo.text = "$jk | $tgl"

            // 4. Set Alamat
            tvAlamat.text = "Alamat: ${pasien.alamat}"

            // 5. Set No HP
            tvNoHp.text = "No. HP: ${pasien.noHp ?: "-"}"
        }
    }

    override fun getItemCount(): Int = listPasien.size
}