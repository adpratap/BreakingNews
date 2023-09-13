package com.noreplypratap.breakingnews.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.databinding.FragmentSavedBinding
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.ui.adapters.NewsAdapter
import com.noreplypratap.breakingnews.utils.showBottomSheetDialog
import com.noreplypratap.breakingnews.viewmodel.RoomDBViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.fragment_saved) {

    private lateinit var binding: FragmentSavedBinding
    private val newsAdapter = NewsAdapter()
    private val localDataViewModel: RoomDBViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged", "InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        binding.rvSavedNews.adapter = newsAdapter

        localDataViewModel.savedArticles.observe(viewLifecycleOwner) {
            it?.let { articles ->
                newsAdapter.differ.submitList(filteredArticles(articles))
                newsAdapter.notifyDataSetChanged()
            }
        }

        newsAdapter.setOnClickListener { article ->
            requireContext().showBottomSheetDialog(article,true, deleteArticle = {
                article.isStared = false
                localDataViewModel.updateArticle(article)
            })
        }
    }

    private fun filteredArticles(articles: List<Article>): List<Article> {
        val tempList: MutableList<Article> = mutableListOf()
        articles.forEach {
            if (it.isStared)
                tempList.add(it)
        }
        return tempList
    }

    override fun onResume() {
        super.onResume()
        val textView = activity?.findViewById<TextView>(R.id.tvAppBar)
        val searchView = activity?.findViewById<SearchView>(R.id.svAppBarSearch)
        val imageButton = activity?.findViewById<ImageButton>(R.id.ibFilterBtn)

        if (searchView != null) {
            searchView.visibility = View.GONE
        }

        if (textView != null) {
            textView.visibility = View.VISIBLE
            textView.text = "Saved News"
        }

        if (imageButton != null) {
            imageButton.visibility = View.GONE
        }

    }
}
