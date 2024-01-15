package com.example.tbc_events.di

import com.example.tbc_events.data.service.IDeleteUsersService
import com.example.tbc_events.data.service.IGetUserService
import com.example.tbc_events.data.service.IGetUsersService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("AllUsers")
    fun provideUsersRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).build()
    }

    @Singleton
    @Provides
    @Named("SingleUser")
    fun provideUserRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).build()
    }

    @Singleton
    @Provides
    fun provideUsersGetService(@Named("AllUsers") retrofit: Retrofit): IGetUsersService {
        return retrofit.create(IGetUsersService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserGetService(@Named("SingleUser") retrofit: Retrofit): IGetUserService {
        return retrofit.create(IGetUserService::class.java)
    }

    @Singleton
    @Provides
    fun provideDeleteUsersService(@Named("SingleUser") retrofit: Retrofit): IDeleteUsersService {
        return retrofit.create(IDeleteUsersService::class.java)
    }

}