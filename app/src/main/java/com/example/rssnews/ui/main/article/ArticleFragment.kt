package com.example.rssnews.ui.main.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssnews.R
import com.example.rssnews.data.Article
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class ArticleFragment : Fragment() {

    companion object {
        fun newInstance() = ArticleFragment()
    }

    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var articles: List<Article>


        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            CoroutineScope(IO).launch {
                try {
                    articles = viewModel.refreshArticles()
                    swipeRefresh.isRefreshing = false
                    if (!viewModel.isConnected) {
                        showNoInternetToast()
                    } else {
                        showSuccessfulRefreshToast()
                    }
                } catch (e: Exception) {
                    swipeRefresh.isRefreshing = false
                    showFailRefreshToast()
                }
            }
        }

        CoroutineScope(IO).launch {
            articles = viewModel.getArticles()

            launch(Main) {
                listView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = ArticleAdapter(articles)
                }
            }
        }
    }

    private fun showNoInternetToast() {
        CoroutineScope(Main).launch {
            Toast.makeText(
                context,
                "No internet connection\nShows old news",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showSuccessfulRefreshToast() {
        CoroutineScope(Main).launch {
            Toast.makeText(
                context,
                "Feed's updated successful",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showFailRefreshToast() {
        CoroutineScope(Main).launch {
            Toast.makeText(
                context,
                "Server error\nShows old news",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}
