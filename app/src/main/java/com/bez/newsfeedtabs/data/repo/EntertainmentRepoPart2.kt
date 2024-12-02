package com.bez.newsfeedtabs.data.repo

import com.bez.newsfeedtabs.domain.model.NewsItem

interface EntertainmentRepoPart2 {
    suspend fun fetchEntertainmentNewsPart2(): List<NewsItem>
}
