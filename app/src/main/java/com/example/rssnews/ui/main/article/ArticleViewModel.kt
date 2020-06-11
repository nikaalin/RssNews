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

    private suspend fun setArticles() {
        articles =
            MutableLiveData(
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    val repo = ArticleCachedRepository
                    repo.getArticles()
                })

    }

    suspend fun getArticles(): List<Article> {
        return withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            if (articles == null)
                setArticles()
            articles!!.value ?: listOf()
        }
    }

}

