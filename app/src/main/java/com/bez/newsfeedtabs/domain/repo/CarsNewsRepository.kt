package com.bez.newsfeedtabs.domain.repo

import com.bez.newsfeedtabs.domain.model.NewsItem

interface CarsNewsRepository {
    suspend fun fetchCarsNews(): List<NewsItem>
}
