package com.example.kycapp.data.remote

import retrofit2.http.GET

interface UsersApi {
    @GET("users")
    suspend fun getUsers(): UserResponse
}