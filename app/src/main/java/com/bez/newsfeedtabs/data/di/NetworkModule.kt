package com.bez.newsfeedtabs.data.di

import com.bez.newsfeedtabs.data.network.NewsService
import com.bez.newsfeedtabs.data.repo.EntertainmentRepoPart1
import com.bez.newsfeedtabs.data.repo.EntertainmentRepoPart2
import com.bez.newsfeedtabs.ui.screen_news.repo.EntertainmentRepoPart1Impl
import com.bez.newsfeedtabs.ui.screen_news.repo.EntertainmentRepoPart2Impl
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
    fun provideEntertainmentNewsPart1Repository(api: NewsService): EntertainmentRepoPart1 {
        return EntertainmentRepoPart1Impl(api)
    }

    @Provides
    @Singleton
    fun provideEntertainmentNewsPart2Repository(api: NewsService): EntertainmentRepoPart2 {
        return EntertainmentRepoPart2Impl(api)
    }

}
