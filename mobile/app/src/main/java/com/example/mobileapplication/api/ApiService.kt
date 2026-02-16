package com.example.mobileapplication.api

import com.example.mobileapplication.models.AuthResponse
import com.example.mobileapplication.models.LoginRequest
import com.example.mobileapplication.models.LogoutRequest
import com.example.mobileapplication.models.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers

interface ApiService {

    @POST("api/auth/register")
    @Headers("Accept: text/plain")
    suspend fun register(@Body request: RegisterRequest): Response<ResponseBody>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/logout")
    suspend fun logout(@Body request: LogoutRequest): Response<String>
}