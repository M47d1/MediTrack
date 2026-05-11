package com.example.meditrack.api

import com.example.meditrack.model.LoginResponse
import com.example.meditrack.model.PasienResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    fun login(@Body loginRequest: Map<String, String>): Call<LoginResponse>

    @GET("api/pasien")
    fun getSemuaPasien(): Call<PasienResponse>
}