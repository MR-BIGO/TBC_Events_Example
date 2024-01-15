package com.example.tbc_events.data.service

import retrofit2.http.DELETE
import retrofit2.http.Path

interface IDeleteUsersService {
    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int)
}