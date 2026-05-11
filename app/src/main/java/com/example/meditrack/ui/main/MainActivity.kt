package com.example.meditrack.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meditrack.api.ApiConfig
import com.example.meditrack.databinding.ActivityMainBinding
import com.example.meditrack.model.PasienResponse
import com.example.meditrack.ui.auth.LoginActivity
import com.example.meditrack.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Tombol Logout
        binding.btnLogout.setOnClickListener {
            sessionManager.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        setupRecyclerView()
        getDataPasien()
    }

    private fun setupRecyclerView() {
        binding.rvPasien.layoutManager = LinearLayoutManager(this)
    }

    private fun getDataPasien() {
        // Berikan context 'this' ke dalam getApiService
        val client = ApiConfig.getApiService(this).getSemuaPasien()

        client.enqueue(object : Callback<PasienResponse> {
            override fun onResponse(call: Call<PasienResponse>, response: Response<PasienResponse>) {
                if (response.isSuccessful) {
                    val list = response.body()?.dataPasien
                    if (!list.isNullOrEmpty()) {
                        binding.rvPasien.adapter = PasienAdapter(list)
                        Toast.makeText(this@MainActivity, "Berhasil memuat ${list.size} data", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Data Kosong atau Key JSON Salah", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Jika error 401 atau 500, akan muncul di sini
                    Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PasienResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Gagal koneksi: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}