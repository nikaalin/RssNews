package com.example.rssnews.data.source

import com.example.rssnews.data.Article

class ArticleRepository private constructor() {
    private lateinit var source: ArticleSource
    private lateinit var articles: MutableList<Article>
    fun getArticles(): List<Article> = articles

    companion object {
        private var instance: ArticleRepository? = null
        suspend fun create(source: ArticleSource): ArticleRepository {
            if (instance == null) {
                instance = ArticleRepository()
                instance!!.source = source
                instance!!.articles = instance!!.source.getArticles()
            }
            return instance as ArticleRepository
        }
    }

    suspend fun update() {
        articles = source.getArticles()
    }

    suspend fun updateBySource(source: ArticleSource) {
        articles = source.getArticles()
    }

    fun setSource(source: ArticleSource) {
        this.source = source
    }
}