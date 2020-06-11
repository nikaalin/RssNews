package com.example.rssnews.data.source

import com.example.rssnews.data.Article

interface ArticleSource {
    suspend fun getArticles(): List<Article>
}