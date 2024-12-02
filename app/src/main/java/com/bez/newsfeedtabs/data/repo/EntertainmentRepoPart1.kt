package com.bez.newsfeedtabs.data.repo

import com.bez.newsfeedtabs.domain.model.NewsItem

interface EntertainmentRepoPart1 {
    suspend fun fetchEntertainmentNewsPart1(): List<NewsItem>
}
