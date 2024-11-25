package com.bez.newsfeedtabs.data.repo

import com.bez.newsfeedtabs.domain.model.NewsItem

interface CarsNewsRepository {
    suspend fun fetchCarsNews(): List<NewsItem>
}
