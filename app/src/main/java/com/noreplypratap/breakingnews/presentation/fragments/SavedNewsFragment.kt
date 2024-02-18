package com.noreplypratap.breakingnews.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.core.utils.showBottomSheetDialog
import com.noreplypratap.breakingnews.databinding.FragmentSavedBinding
import com.noreplypratap.breakingnews.presentation.adapters.NewsAdapter
import com.noreplypratap.breakingnews.presentation.viewmodels.LocalNewsViewModel
import com.noreplypratap.domain.model.NewsArticle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved) {

    private lateinit var binding: FragmentSavedBinding
    private val newsAdapter = NewsAdapter()
    private val localNewsViewModel: LocalNewsViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged", "InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        binding.rvSavedNews.adapter = newsAdapter

        //localNewsViewModel.readArticle()
        localNewsViewModel.savedArticles.observe(viewLifecycleOwner) {
            it?.let { articles ->
                newsAdapter.differ.submitList(articles)
                newsAdapter.notifyDataSetChanged()
            }
        }

        newsAdapter.setOnClickListener { article ->
            requireContext().showBottomSheetDialog(article,true, deleteArticle = {
                localNewsViewModel.deleteArticle(article)
                //localNewsViewModel.readArticle()
                newsAdapter.notifyDataSetChanged()
            })
        }
    }

    override fun onResume() {
        super.onResume()
        //localNewsViewModel.readArticle()
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
