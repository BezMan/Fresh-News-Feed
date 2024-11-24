package com.bez.newsfeedtabs.domain.repo

import com.bez.newsfeedtabs.domain.model.NewsItem

interface EntertainmentNewsRepository {
    suspend fun fetchEntertainmentNewsPart1(): List<NewsItem>
    suspend fun fetchEntertainmentNewsPart2(): List<NewsItem>
}
