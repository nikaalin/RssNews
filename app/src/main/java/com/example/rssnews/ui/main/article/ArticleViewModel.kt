package com.example.rssnews.ui.main.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rssnews.data.Article
import com.example.rssnews.data.source.ArticleCachedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ArticleViewModel : ViewModel() {
    private var isConnected: Boolean = false
    private var lastRefreshTime: String = "never"

    var articlesLiveData: MutableLiveData<List<Article>?> = MutableLiveData()
    var isRefreshingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    var refreshStatusLiveData: MutableLiveData<RefreshStatus> = MutableLiveData()

    var refreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefreshingLiveData.value = true
        CoroutineScope(Main).launch {
            try {
                refreshArticles()
                isRefreshingLiveData.value = false
                if (!isConnected) {
                    refreshStatusLiveData.value = RefreshStatus.NO_CONNECTION
                } else {
                    refreshStatusLiveData.value = RefreshStatus.SUCCESS
                }
            } catch (e: Exception) {
                CoroutineScope(Main).launch {
                    isRefreshingLiveData.value = false
                    refreshStatusLiveData.value = RefreshStatus.FAIL
                }
            }
        }
    }


    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            refreshArticles()
        }
    }

    private suspend fun refreshArticles() {
        CoroutineScope(Main).launch {
            articlesLiveData.value =
                withContext(Dispatchers.IO) {
                    val repo = ArticleCachedRepository
                    isConnected = repo.isNetworkConnected
                    lastRefreshTime = repo.lastUpdateTime.toString()
                    repo.getArticles()
                }
        }
    }
}

enum class RefreshStatus {
    SUCCESS, FAIL, NO_CONNECTION
}

