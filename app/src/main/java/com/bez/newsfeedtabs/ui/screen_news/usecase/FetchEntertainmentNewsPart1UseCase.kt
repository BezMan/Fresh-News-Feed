package com.bez.newsfeedtabs.ui.screen_news.usecase

import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.domain.repo.EntertainmentNewsRepository
import javax.inject.Inject

class FetchEntertainmentNewsPart1UseCase @Inject constructor(
    private val repository: EntertainmentNewsRepository
) {
    suspend operator fun invoke(): List<NewsItem> {
        return repository.fetchEntertainmentNewsPart1()
    }
}
