package com.example.tbc_events.di

import com.example.tbc_events.data.common.HandleResponse
import com.example.tbc_events.data.repository.DeleteUsersRepositoryImpl
import com.example.tbc_events.data.repository.GetUserRepositoryImpl
import com.example.tbc_events.data.repository.GetUsersRepositoryImpl
import com.example.tbc_events.data.service.IDeleteUsersService
import com.example.tbc_events.data.service.IGetUserService
import com.example.tbc_events.data.service.IGetUsersService
import com.example.tbc_events.domain.repository.IDeleteUsersRepository
import com.example.tbc_events.domain.repository.IGetUserRepository
import com.example.tbc_events.domain.repository.IGetUsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGetUsersRepository(
        getService: IGetUsersService,
        handler: HandleResponse
    ): IGetUsersRepository {
        return GetUsersRepositoryImpl(getService, handler)
    }

    @Singleton
    @Provides
    fun provideGetUserRepository(
        getService: IGetUserService,
        handler: HandleResponse
    ): IGetUserRepository {
        return GetUserRepositoryImpl(getService, handler)
    }

    @Singleton
    @Provides
    fun provideDeleteUsersRepository(deleteService: IDeleteUsersService): IDeleteUsersRepository {
        return DeleteUsersRepositoryImpl(deleteService)
    }


    @Singleton
    @Provides
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }
}