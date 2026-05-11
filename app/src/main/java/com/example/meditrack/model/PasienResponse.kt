package com.example.meditrack.model

import com.google.gson.annotations.SerializedName

data class PasienResponse(
    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("data") // Laravel biasanya pakai "data"
    val dataPasien: List<PasienItem>?
)

data class PasienItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nama")
    val nama: String,

    // PERBAIKAN: Gunakan underscore, bukan spasi.
    // Tambahkan tanda tanya (?) agar tidak crash jika data dari server kosong.
    @SerializedName("tanggal_lahir")
    val tanggalLahir: String?,

    @SerializedName("jenis_kelamin")
    val jenisKelamin: String?,

    @SerializedName("alamat")
    val alamat: String,

    // Sesuaikan dengan data viewer tadi: no_telepon (bukan no_hp)
    @SerializedName("no_telepon")
    val noHp: String?
)