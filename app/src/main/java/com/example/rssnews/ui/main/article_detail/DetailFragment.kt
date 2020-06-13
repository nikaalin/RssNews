package com.example.rssnews.ui.main.article_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.rssnews.R
import com.example.rssnews.data.Article
import com.example.rssnews.databinding.ArticleDetailFragmentBinding


class DetailFragment : Fragment() {

    companion object {
        lateinit var article: Article
        fun newInstance(article: Article): DetailFragment {
            this.article = article
            return DetailFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: ArticleDetailFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.article_detail_fragment, container, false)
        binding.article = article
        return binding.root
    }


}
