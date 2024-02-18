package com.noreplypratap.breakingnews.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.noreplypratap.breakingnews.databinding.NewsFragmentBinding
import com.noreplypratap.domain.model.NewsArticle

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    class ArticleViewHolder(val binding: NewsFragmentBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<NewsArticle>(){
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        val binding = NewsFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            artical = article
            root.setOnClickListener {
                onItemClicked?.let {
                    it(article)
                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClicked : ((NewsArticle) -> Unit)? = null
    fun setOnClickListener(listener : (NewsArticle) -> Unit){
        onItemClicked = listener
    }
}