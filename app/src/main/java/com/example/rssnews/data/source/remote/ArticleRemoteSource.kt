package com.example.rssnews.data.source.remote

import android.util.Log
import com.example.rssnews.data.Article
import com.example.rssnews.data.source.ArticleSource

class ArticleRemoteSource : ArticleSource {
    private val api: RssAPI by lazy { RssService.createAPI() }

    override suspend fun getArticles(): MutableList<Article> {
        val resultRss = api.sendRequest().await()
        Log.d("TIME IS ", resultRss.channel!!.items!![0].pubDate.toString())
        return resultRss.channel!!.items!!.convertAllToModel()
    }
}