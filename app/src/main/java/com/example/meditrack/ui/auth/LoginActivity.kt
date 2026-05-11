package com.example.meditrack.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.meditrack.api.ApiConfig
import com.example.meditrack.databinding.ActivityLoginBinding
import com.example.meditrack.model.LoginResponse
import com.example.meditrack.ui.main.MainActivity
import com.example.meditrack.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        binding.btnLogin.setOnClickListener {
            // Gunakan email dan password yang diberikan Dodi:
            // admin@example.com / password
            val email = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginData = mapOf("email" to email, "password" to password)

                // Memanggil ApiConfig dengan context 'this'
                ApiConfig.getApiService(this).login(loginData).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val body = response.body()

                            // Cek semua kemungkinan lokasi token
                            val tokenUtama = body?.token
                                ?: body?.accessToken
                                ?: body?.data?.token
                                ?: body?.data?.accessToken

                            if (!tokenUtama.isNullOrEmpty()) {
                                sessionManager.setLogin(true, tokenUtama)

                                android.util.Log.d("DEBUG_TOKEN", "Berhasil Simpan Token: $tokenUtama")

                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            } else {
                                // Kalau ini masih muncul, berarti struktur JSON server benar-benar beda
                                Toast.makeText(this@LoginActivity, "Token tidak ditemukan di JSON", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, "Login Gagal: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        // Jika koneksi internet bermasalah
                        Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}