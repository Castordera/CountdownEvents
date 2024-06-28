package com.example.countdownapp.di

import com.ulises.common.database.datasource.RoomCountdownDataSource
import com.ulises.data.DataStorePreferences
import com.ulises.data.datasource.CountdownLocalDataSource
import com.ulises.datastore.source.DataStoreBooleanDataSource
import com.ulises.datastore.source.DataStoreStringDataSource
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
    abstract fun bindBooleanDataStore(imp: DataStoreBooleanDataSource): DataStorePreferences<Boolean>

    @Binds
    abstract fun bindStringDataStore(imp: DataStoreStringDataSource): DataStorePreferences<String>
}