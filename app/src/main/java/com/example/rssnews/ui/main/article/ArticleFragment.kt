package com.example.rssnews.ui.main.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssnews.R
import com.example.rssnews.data.Article
import com.example.rssnews.databinding.ArticleListFragmentBinding
import com.example.rssnews.ui.main.article.RefreshStatus.*
import com.example.rssnews.ui.main.article_detail.DetailFragment


class ArticleFragment : Fragment() {

    companion object {
        fun newInstance() = ArticleFragment()
    }

    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        viewModel.init()

        val binding: ArticleListFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.article_list_fragment, container, false)
        binding.viewModel = viewModel

        val articleAdapter = ArticleAdapter()
        articleAdapter.listener = object : ArticleAdapter.ItemClick {
            override fun onItemClick(article: Article) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, DetailFragment.newInstance(article))
                    .addToBackStack(null)
                    .commit()
            }
        }
        with(binding.articlesListRV) {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            articleAdapter.list = it!!
        })

        viewModel.refreshStatusLiveData.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    FAIL -> showLongToast(R.string.fail_toast)
                    SUCCESS -> showLongToast(R.string.success_toast)
                    NO_CONNECTION -> showLongToast(R.string.noconnection_toast)
                    null -> return@Observer
                }
            }
        )

        viewModel.isRefreshingLiveData.observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.isRefreshing = it
        })

        return binding.root
    }

    private fun showLongToast(@StringRes msg: Int) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }
}


