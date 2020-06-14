package com.example.rssnews.data.source.remote

import com.example.rssnews.data.Article
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Root(name = "rss", strict = false)
class Rss {
    @field:Element(name = "channel")
    var channel: Channel? = null
}

@Root(name = "channel", strict = false)
class Channel {
    @field:Element(name = "title")
    var title: String? = null

    @field:ElementList(inline = true, required = false)
    var items: MutableList<Item>? = null
}

@Root(name = "item", strict = false)
class Item {
    @field:Element(name = "title")
    var title: String? = null

    @field:Element(name = "link")
    var link: String? = null

    @field:Element(name = "creator")
    var creator: String? = null

    @field:ElementList(entry = "category", required = false, inline = true)
    var categories: List<String?>? = null

    @field:Element(name = "description")
    var description: String? = null

    @field:Element(name = "pubDate")
    var pubDate: String? = null
}

fun MutableList<Item>.convertAllToModel(): MutableList<Article> = this.map { i ->
    convertToModel(i)
} as MutableList<Article>

private fun convertToModel(serverModel: Item): Article =
    Article(
        null,
        serverModel.title ?: "",
        serverModel.link ?: "",
        serverModel.creator ?: "",
        serverModel.categories?.map { it ?: "" } ?: listOf(),
        serverModel.description ?: "",
        LocalDateTime.parse(serverModel.pubDate, DateTimeFormatter.RFC_1123_DATE_TIME)
    )
