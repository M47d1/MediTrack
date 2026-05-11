package com.example.meditrack.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("message") val message: String?,

    // Server mungkin kirim salah satu dari ini, kita siapkan penampungnya
    @SerializedName("token") val token: String?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("data") val data: LoginData?
)

data class LoginData(
    @SerializedName("token") val token: String?,
    @SerializedName("access_token") val accessToken: String?
)