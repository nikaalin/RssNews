package com.example.rssnews.ui.main.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.rssnews.BR
import com.example.rssnews.R
import com.example.rssnews.data.Article


class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ItemViewHolder>() {

    var list: List<Article> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.article_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val article: Article = list[position]
        holder.binding.setVariable(BR.item, article)
    }

    override fun getItemCount(): Int = list.size


    inner class ItemViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}

