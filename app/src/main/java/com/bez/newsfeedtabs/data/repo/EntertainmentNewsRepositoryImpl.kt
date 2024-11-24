package com.bez.newsfeedtabs.data.repo

import com.bez.newsfeedtabs.data.api.NewsService
import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.domain.repo.EntertainmentNewsRepository

class EntertainmentNewsRepositoryImpl(private val api: NewsService) : EntertainmentNewsRepository {

    override suspend fun fetchEntertainmentNewsPart1(): List<NewsItem> {
        return api.fetchEntertainmentNewsPart1().channel.items
    }

    override suspend fun fetchEntertainmentNewsPart2(): List<NewsItem> {
        return api.fetchEntertainmentNewsPart2().channel.items
    }
}
