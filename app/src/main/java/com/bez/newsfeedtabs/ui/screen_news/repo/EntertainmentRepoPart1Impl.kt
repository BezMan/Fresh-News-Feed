package com.bez.newsfeedtabs.ui.screen_news.repo

import com.bez.newsfeedtabs.data.network.NewsService
import com.bez.newsfeedtabs.domain.model.NewsItem
import com.bez.newsfeedtabs.data.repo.EntertainmentRepoPart1
import javax.inject.Inject

class EntertainmentRepoPart1Impl @Inject constructor(
    private val api: NewsService
) : EntertainmentRepoPart1 {

    override suspend fun fetchEntertainmentNewsPart1(): List<NewsItem> {
        return api.fetchEntertainmentNewsPart1().channel.items
    }

}
