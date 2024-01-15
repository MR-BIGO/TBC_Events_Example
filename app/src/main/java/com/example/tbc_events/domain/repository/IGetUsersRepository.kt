package com.example.tbc_events.domain.repository

import com.example.tbc_events.data.common.Resource
import com.example.tbc_events.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IGetUsersRepository {
    suspend fun getUsers(): Flow<Resource<List<User>>>
}