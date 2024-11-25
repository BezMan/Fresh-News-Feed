package com.bez.newsfeedtabs.domain.usecase

import com.bez.newsfeedtabs.data.repo.CarsNewsRepository
import com.bez.newsfeedtabs.domain.model.NewsItem
import javax.inject.Inject

class FetchCarsUC @Inject constructor(private val repository: CarsNewsRepository) {
    suspend operator fun invoke(): List<NewsItem> {
        return repository.fetchCarsNews()
    }
}
