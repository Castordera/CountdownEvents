package com.example.countdownapp.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class PreferencesFileName

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class HumanReadableFormat

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DatabaseCounterName

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DataStoreListViewType