package com.example.rssnews.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rssnews.R
import com.example.rssnews.data.Article
import kotlinx.android.synthetic.main.item.view.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item, parent, false)) {


    fun bind(article: Article) {
        itemView.itemTitle.text = article.title
        itemView.itemAuthor.text = article.creator
        itemView.itemTime.text = article.date.format(
            DateTimeFormatter.ofLocalizedDateTime(
                FormatStyle.MEDIUM,
                FormatStyle.SHORT
            )
        )
    }
}

class ListAdapter(private val list: MutableList<Article>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val movie: Article = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

}

