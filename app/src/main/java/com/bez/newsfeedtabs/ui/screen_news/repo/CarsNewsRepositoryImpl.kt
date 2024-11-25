package com.bez.newsfeedtabs.ui.screen_news.repo

import com.bez.newsfeedtabs.data.network.NewsService
import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.data.repo.CarsNewsRepository

class CarsNewsRepositoryImpl(private val api: NewsService) : CarsNewsRepository {
    override suspend fun fetchCarsNews(): List<NewsItem> {
        return api.fetchCarsNews().channel.items
    }
}
