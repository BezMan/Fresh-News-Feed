package com.bez.newsfeedtabs.data.repo

import com.bez.newsfeedtabs.data.api.NewsService
import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.domain.repo.CarsNewsRepository

class CarsNewsRepositoryImpl(private val api: NewsService) : CarsNewsRepository {
    override suspend fun fetchCarsNews(): List<NewsItem> {
        return api.fetchCarsNews().channel.items
    }
}
