package com.example.countdownapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnnotationsModule {

    @Provides
    @Singleton
    @PreferencesFileName
    fun providePreferencesName(): String = "local-data-preferences"

    @Provides
    @Singleton
    @HumanReadableFormat
    fun provideHumanReadableFormat(): DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
}