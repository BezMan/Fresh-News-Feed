package com.bez.newsfeedtabs.data

import com.bez.newsfeedtabs.data.api.NewsService
import com.bez.newsfeedtabs.data.repo.EntertainmentNewsRepositoryImpl
import com.bez.newsfeedtabs.domain.repo.EntertainmentNewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://www.ynet.co.il/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun provideEntertainmentNewsRepository(api: NewsService): EntertainmentNewsRepository {
        return EntertainmentNewsRepositoryImpl(api)
    }

}
