package com.example.tbc_events.data.service

import com.example.tbc_events.data.dto.SingleResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IGetUserService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<SingleResponseDto>
}