package com.example.rssnews.data.source.local

import android.content.Context
import com.example.rssnews.data.Article
import com.example.rssnews.data.source.ArticleSource

class ArticleLocalSource(context: Context) : ArticleSource {
    private val db: ArticleDatabase? by lazy {
        ArticleDatabase.getAppDataBase(context)
    }
    private val articleDao: ArticleDao? by lazy { db?.articleDao() }

    override suspend fun getArticles(): MutableList<Article> =
        articleDao!!.getArticles().convertAllToModel()

    fun update(articles: List<Article>) {
        articleDao!!.clearArticles()
        articles.convertAllToDbModel().forEach { articleDao!!.insertArticle(it) }
    }
}