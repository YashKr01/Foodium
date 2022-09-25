package com.techK.foodium.di

import com.techK.foodium.data.datastore.RecipesDataStore
import com.techK.foodium.data.datastore.RecipesDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    abstract fun provideDataStore(preferenceStorage: RecipesDataStoreImpl): RecipesDataStore

}