package com.example.tbc_events.domain.use_case

import com.example.tbc_events.data.common.Resource
import com.example.tbc_events.data.mapper.mapListToDomain
import com.example.tbc_events.presentation.mapper.toPresentation
import com.example.tbc_events.domain.repository.IGetUsersRepository
import com.example.tbc_events.presentation.model.UserItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: IGetUsersRepository) {

    suspend operator fun invoke(): Flow<Resource<List<UserItem>>> {
        return repository.getUsers().mapListToDomain { it.toPresentation() }
    }
}