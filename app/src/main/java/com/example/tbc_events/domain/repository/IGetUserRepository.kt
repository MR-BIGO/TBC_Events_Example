package com.example.tbc_events.domain.repository

import com.example.tbc_events.data.common.Resource
import com.example.tbc_events.domain.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

interface IGetUserRepository {
    suspend fun getUser(@Body id: Int): Flow<Resource<User>>
}