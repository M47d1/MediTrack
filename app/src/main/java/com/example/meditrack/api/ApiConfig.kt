package com.example.meditrack.api

import android.content.Context // 1. Pastikan ini ada
import com.example.meditrack.utils.SessionManager // 2. Pastikan ini ada
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        // 3. Tambahkan parameter context di dalam kurung fungsi ini
        fun getApiService(context: Context): ApiService {

            // Sekarang ini tidak akan error lagi
            val sessionManager = SessionManager(context)

            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val token = sessionManager.getToken()
                    val requestBuilder = chain.request().newBuilder()
                        .addHeader("Accept", "application/json") // WAJIB: Supaya server kirim JSON, bukan HTML
                        .addHeader("Content-Type", "application/json") // Tambahkan ini juga

                    if (token != null) {
                        requestBuilder.addHeader("Authorization", "Bearer $token")
                    }
                    chain.proceed(requestBuilder.build())
                }
                .build()

            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.pahrul.my.id/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}