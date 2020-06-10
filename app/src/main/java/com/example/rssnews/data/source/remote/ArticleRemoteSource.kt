package com.example.rssnews.data.source.remote

import com.example.rssnews.data.Article
import com.example.rssnews.data.source.ArticleSource

class ArticleRemoteSource : ArticleSource {
    private val api: RssAPI by lazy { RssService.createAPI() }

    override suspend fun getArticles(): MutableList<Article> {
        val resultRss = api.sendRequest().await()
        return resultRss.channel!!.items!!.convertAllToModel()
    }
}