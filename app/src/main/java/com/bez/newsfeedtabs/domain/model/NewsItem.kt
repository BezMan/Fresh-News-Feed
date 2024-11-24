package com.bez.newsfeedtabs.domain.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class NewsFeed(
    @field:Element(name = "channel")
    var channel: Channel
)

@Root(name = "channel", strict = false)
data class Channel(
    @field:ElementList(name = "item", inline = true)
    var items: List<NewsItem>
)

@Root(name = "item", strict = false)
data class NewsItem(
    @field:Element(name = "title")
    var title: String,

    @field:Element(name = "link")
    var link: String,

    @field:Element(name = "pubDate", required = false)
    var pubDate: String? = null,

    @field:Element(name = "description", required = false)
    var description: String? = null
)
