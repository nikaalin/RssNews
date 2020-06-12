package com.example.rssnews.ui.main.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rssnews.data.Article
import com.example.rssnews.data.source.ArticleCachedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleViewModel : ViewModel() {
    private var articles: MutableLiveData<List<Article>>? = null
    var isConnected: Boolean = false
    var lastRefreshTime: String = "never"

    suspend fun getArticles(): List<Article> {
        return withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            if (articles == null)
                refreshArticles()
            articles!!.value ?: listOf()
        }
    }

    suspend fun refreshArticles(): List<Article> {
        articles =
            MutableLiveData(
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    val repo = ArticleCachedRepository
                    isConnected = repo.isNetworkConnected
                    lastRefreshTime = repo.lastUpdateTime.toString()
                    repo.getArticles()
                })
        return articles!!.value ?: listOf()
    }


}

