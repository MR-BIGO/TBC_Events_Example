package com.example.tbc_events.data.repository

import com.example.tbc_events.data.service.IDeleteUsersService
import com.example.tbc_events.domain.repository.IDeleteUsersRepository
import javax.inject.Inject

class DeleteUsersRepositoryImpl @Inject constructor(
    private val deleteService: IDeleteUsersService,
) :
    IDeleteUsersRepository {
    override suspend fun deleteUser(id: Int) {
        deleteService.deleteUser(id)
    }
}