package com.noreplypratap.breakingnews.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.utils.Constants

class SearchNewsAdapter(private var articles : MutableList<Article>) : RecyclerView.Adapter<SearchNewsAdapter.ArticleViewHolder>() {

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

    @SuppressLint("NotifyDataSetChanged")
    fun newList(nList : MutableList<Article>){
        articles = nList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_fragment,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article  = articles[position]

        holder.itemView.apply {
            if(article.urlToImage.isNullOrBlank()){
                Glide.with(this).load(Constants.DefaultImageURl)
                    .into(holder.imageview)
            }else{
                Glide.with(this).load(article.urlToImage)
                    .into(holder.imageview)
            }

            holder.textView.text = article.title

            setOnClickListener {
                onItemClicked?.let {
                    it(article)
                }
            }
        }

    }

    override fun getItemCount() = articles.size

    private var onItemClicked : ((Article) -> Unit)? = null

    fun setOnClickListener(listener : (Article) -> Unit){
        onItemClicked = listener
    }
}