package com.bez.newsfeedtabs.ui.screen_news.repo

import com.bez.newsfeedtabs.data.network.NewsService
import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.data.repo.EntertainmentNewsRepository
import javax.inject.Inject

class EntertainmentNewsRepositoryImpl @Inject constructor(
    private val api: NewsService
) : EntertainmentNewsRepository {

    override suspend fun fetchEntertainmentNewsPart1(): List<NewsItem> {
        return api.fetchEntertainmentNewsPart1().channel.items
    }

    override suspend fun fetchEntertainmentNewsPart2(): List<NewsItem> {
        return api.fetchEntertainmentNewsPart2().channel.items
    }
}
