package com.example.tbc_events.data.repository

import com.example.tbc_events.data.common.HandleResponse
import com.example.tbc_events.data.common.Resource
import com.example.tbc_events.data.mapper.mapToDomain
import com.example.tbc_events.data.mapper.toDomain
import com.example.tbc_events.data.service.IGetUserService
import com.example.tbc_events.domain.model.User
import com.example.tbc_events.domain.repository.IGetUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRepositoryImpl @Inject constructor(
    private val service: IGetUserService,
    private val handler: HandleResponse
) : IGetUserRepository {
    override suspend fun getUser(id: Int): Flow<Resource<User>> {
        return handler.safeApiCall {
            service.getUser(id)
        }.mapToDomain { it.data.toDomain() }
    }
}