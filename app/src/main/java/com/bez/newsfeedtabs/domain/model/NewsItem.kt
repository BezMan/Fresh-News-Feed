package com.bez.newsfeedtabs.domain.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class NewsItem(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String? = null,

    @field:Element(name = "description")
    @param:Element(name = "description")
    val description: String? = null,

    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String? = null,

    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    val pubDate: String? = null,

    @field:Element(name = "guid")
    @param:Element(name = "guid")
    val guid: String? = null,

    @field:Element(name = "tags")
    @param:Element(name = "tags")
    val tags: String? = null
)


@Root(name = "rss", strict = false)
data class NewsFeed(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: Channel
)

@Root(name = "channel", strict = false)
data class Channel(
    @field:ElementList(name = "item", inline = true)
    @param:ElementList(name = "item", inline = true)
    val items: List<NewsItem> = emptyList()
)
