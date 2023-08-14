package com.example.countdownapp.di

import com.ulises.data.repositories.CountdownRepository
import com.ulises.data.repositories.CountdownRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCountdownRepository(repository: CountdownRepositoryImpl): CountdownRepository
}