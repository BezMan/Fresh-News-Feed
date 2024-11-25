package com.bez.newsfeedtabs.data.di

import android.app.Application
import android.content.Context
import com.bez.newsfeedtabs.data.local.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideDataStoreManager(context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

}
