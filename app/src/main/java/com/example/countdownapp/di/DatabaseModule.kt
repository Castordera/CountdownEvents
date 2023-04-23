package com.example.countdownapp.di

import android.app.Application
import androidx.room.Room
import com.example.countdownapp.data.database.CountdownDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(
        app: Application,
        @DatabaseCounterName databaseName: String
    ) = Room.databaseBuilder(
        app,
        CountdownDatabase::class.java,
        databaseName
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideCounterDao(database: CountdownDatabase) = database.counterDao()
}