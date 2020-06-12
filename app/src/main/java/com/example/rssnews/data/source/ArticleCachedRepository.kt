package com.example.rssnews.data.source

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.example.rssnews.RssApplication
import com.example.rssnews.data.Article
import com.example.rssnews.data.source.local.ArticleLocalSource
import com.example.rssnews.data.source.remote.ArticleRemoteSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.time.LocalDateTime


object ArticleCachedRepository : ArticleSource {
    var isNetworkConnected: Boolean = false
    var lastUpdateTime: LocalDateTime? = null

    private val remoteSource = ArticleRemoteSource()
    private val localSource = ArticleLocalSource(RssApplication.getAppContext()!!)

    private var localData: List<Article>? = null

    init {
        registerNetworkCallback()
    }

    override suspend fun getArticles(): List<Article> {
        if (isNetworkConnected) {
            updateRepositoryByRemote()
        } else if (localData == null) {
            updateRepositoryByLocal()
        }
        return localData!!
    }


    private suspend fun updateRepositoryByRemote() {
        lastUpdateTime = LocalDateTime.now()
        val oldData = localData

        localData = remoteSource.getArticles()

        (localData as MutableList<Article>).forEach {
            if (!oldData!!.contains(it)) {
                localSource.saveArticle(it)
            }
        }
    }

    private suspend fun updateRepositoryByLocal() {
        localData = withContext(IO) {
            localSource.getArticles()
        }
    }

    private fun registerNetworkCallback() {
        try {
            val connectivityManager =
                RssApplication.getAppContext()!!
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            NetworkRequest.Builder()
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isNetworkConnected = true
                }

                override fun onLost(network: Network) {
                    isNetworkConnected = false
                }
            })
            isNetworkConnected = false
        } catch (e: Exception) {
            isNetworkConnected = false
        }
    }

}

