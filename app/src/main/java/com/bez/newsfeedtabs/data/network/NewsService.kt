package com.bez.newsfeedtabs.data.network

import com.bez.newsfeedtabs.domain.model.NewsFeed
import retrofit2.http.GET

interface NewsService {

    @GET("Integration/StoryRss550.xml")
    suspend fun fetchCarsNews(): NewsFeed

    @GET("Integration/StoryRss3.xml")
    suspend fun fetchEntertainmentNewsPart1(): NewsFeed

    @GET("Integration/StoryRss538.xml")
    suspend fun fetchEntertainmentNewsPart2(): NewsFeed
}
