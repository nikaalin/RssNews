package com.example.rssnews.data.source

import com.example.rssnews.data.Article

class ArticleRepository private constructor() {
    private lateinit var articles: MutableList<Article>
    fun getArticles(): MutableList<Article> = articles

    companion object {
        private var instance: ArticleRepository? = null
        suspend fun create(source: ArticleSource): ArticleRepository {
            if (instance == null) {
                instance = ArticleRepository()
                instance!!.articles = source.getArticles()
            }
            return instance as ArticleRepository
        }
    }
}