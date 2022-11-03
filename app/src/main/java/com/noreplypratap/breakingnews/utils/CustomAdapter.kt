package com.noreplypratap.breakingnews.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.model.Article


class CustomAdapter(private val dataSet: List<Article>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var onItemClick: ((Article) -> Unit)? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView
        val textView2: TextView
        val imageview: ImageView

        init {
            textView = view.findViewById(R.id.tvFNewsHeading)
            textView2 = view.findViewById(R.id.tvFNewsBody)
            imageview = view.findViewById(R.id.ivFNewsImage)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.news_fragment, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        Glide.with(viewHolder.itemView).load(dataSet[position].urlToImage)
            .into(viewHolder.imageview)
        viewHolder.textView.text = dataSet[position].title
        viewHolder.textView2.text = dataSet[position].description


        viewHolder.itemView.setOnClickListener {

            onItemClick?.invoke(dataSet[position])

        }
    }

    override fun getItemCount() = dataSet.size - 1

}
