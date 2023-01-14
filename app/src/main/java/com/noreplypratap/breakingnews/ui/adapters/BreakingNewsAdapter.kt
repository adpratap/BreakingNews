package com.noreplypratap.breakingnews.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.utils.Constants
import com.noreplypratap.breakingnews.utils.glide

class BreakingNewsAdapter : RecyclerView.Adapter<BreakingNewsAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView
        var textView2: TextView
        var imageview: ImageView

        init {
            textView = itemView.findViewById(R.id.tvFNewsHeading)
            textView2 = itemView.findViewById(R.id.tvFNewsBody)
            imageview = itemView.findViewById(R.id.ivFNewsImage)
        }
    }


    private val diffCallBack = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_fragment,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article = differ.currentList[position]

        holder.itemView.apply {
            if(article.urlToImage.isNullOrBlank()){
                context.glide(Constants.DefaultImageURl,holder.imageview)
            }else{
                context.glide(article.urlToImage,holder.imageview)
            }
            holder.textView.text = article.title

            setOnClickListener {
                onItemClicked?.let {
                    it(article)
                }
            }
        }

    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClicked : ((Article) -> Unit)? = null

    fun setOnClickListener(listener : (Article) -> Unit){
        onItemClicked = listener
    }
}