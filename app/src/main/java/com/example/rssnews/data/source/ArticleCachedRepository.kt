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


object ArticleCachedRepository : ArticleSource {
    private val remoteSource = ArticleRemoteSource()
    private val localSource = ArticleLocalSource(RssApplication.getAppContext()!!)

    private var isNetworkConnected: Boolean = false
    private var localData: List<Article>? = null

    init {
        registerNetworkCallback()
    }

    override suspend fun getArticles(): List<Article> {
        if (isNetworkConnected) {
            localData = remoteSource.getArticles()
            localSource.saveArticles(localData!!)
        }
        if (localData == null) {
            localData = withContext(IO) {
                localSource.getArticles()
            }
        }
        return localData!!
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

