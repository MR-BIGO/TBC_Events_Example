package com.example.tbc_events.domain.use_case

import com.example.tbc_events.data.common.Resource
import com.example.tbc_events.data.mapper.mapToDomain
import com.example.tbc_events.presentation.mapper.toPresentation
import com.example.tbc_events.domain.repository.IGetUserRepository
import com.example.tbc_events.presentation.model.UserItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: IGetUserRepository) {

    suspend operator fun invoke(id: Int): Flow<Resource<UserItem>>{
        return repository.getUser(id).mapToDomain { it.toPresentation() }
    }
}