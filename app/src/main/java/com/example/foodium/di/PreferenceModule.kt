package com.example.foodium.di

import com.example.foodium.data.datastore.PreferenceStorage
import com.example.foodium.data.datastore.PreferenceStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    abstract fun provideDataStore(preferenceStorage: PreferenceStorage): PreferenceStorageImpl

}