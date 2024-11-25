package com.bez.newsfeedtabs.domain.usecase

import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.data.repo.EntertainmentNewsRepository
import javax.inject.Inject

class FetchCarsUC @Inject constructor(private val repository: EntertainmentNewsRepository) {
    suspend operator fun invoke(): List<NewsItem> {
        return repository.fetchEntertainmentNewsPart1()
    }
}
