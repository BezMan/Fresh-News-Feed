package com.bez.newsfeedtabs.ui.screen_news.repo

import com.bez.newsfeedtabs.data.network.NewsService
import com.bez.newsfeedtabs.data.repo.EntertainmentRepoPart2
import com.bez.newsfeedtabs.domain.model.NewsItem
import javax.inject.Inject

class EntertainmentRepoPart2Impl @Inject constructor(
    private val api: NewsService
) : EntertainmentRepoPart2 {

    override suspend fun fetchEntertainmentNewsPart2(): List<NewsItem> {
        return api.fetchEntertainmentNewsPart2().channel.items
    }
}
