package com.example.countdownapp.di

import com.example.countdownapp.data.database.datasource.RoomCountdownDataSource
import com.example.countdownapp.data.datastore.DataStoreDataSource
import com.ulises.data.DataStorePreferences
import com.ulises.data.datasource.CountdownLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindRoomDataSource(dataSource: RoomCountdownDataSource): CountdownLocalDataSource

    @Binds
//    @DataStoreListViewType
    abstract fun bindDataStoreListViewType(imp: DataStoreDataSource): DataStorePreferences<Boolean>
}