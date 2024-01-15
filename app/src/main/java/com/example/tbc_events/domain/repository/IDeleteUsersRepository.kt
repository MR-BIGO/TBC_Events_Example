package com.example.tbc_events.domain.repository

import retrofit2.http.Body

interface IDeleteUsersRepository {
    suspend fun deleteUser(@Body id: Int)
}