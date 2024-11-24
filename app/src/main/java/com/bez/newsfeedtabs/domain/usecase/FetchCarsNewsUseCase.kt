package com.bez.newsfeedtabs.domain.usecase

import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.domain.repo.EntertainmentNewsRepository

class FetchCarsNewsUseCase(private val repository: EntertainmentNewsRepository) {
    suspend operator fun invoke(): List<NewsItem> {
        return repository.fetchEntertainmentNewsPart1()
    }
}
