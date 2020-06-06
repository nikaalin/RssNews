package com.example.rssnews.repo

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

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
    @field:ElementList(inline = true, required = false)
    var categories: MutableList<String>? = null
    @field:Element(name = "description")
    var description: String? = null
}


interface RssService {
    @GET("feed/")
    fun getFeed(): Call<Rss>
}

class Service {
    companion object {
        fun create(): RssService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    SimpleXmlConverterFactory.create()
                )
                .baseUrl("https://blog.humblebundle.com/")
                .build()

            return retrofit.create(RssService::class.java)
        }
    }


}



