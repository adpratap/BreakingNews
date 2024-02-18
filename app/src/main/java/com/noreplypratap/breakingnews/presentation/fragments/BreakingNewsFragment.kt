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
import com.noreplypratap.breakingnews.databinding.FragmentBreakingNewsBinding
import com.noreplypratap.breakingnews.presentation.adapters.NewsAdapter
import com.noreplypratap.breakingnews.core.utils.codeBrazil
import com.noreplypratap.breakingnews.core.utils.codeCanada
import com.noreplypratap.breakingnews.core.utils.codeIndia
import com.noreplypratap.breakingnews.core.utils.codeRussia
import com.noreplypratap.breakingnews.core.utils.codeSwitzerland
import com.noreplypratap.breakingnews.core.utils.codeUS
import com.noreplypratap.breakingnews.core.utils.isOnline
import com.noreplypratap.breakingnews.core.utils.logMessage
import com.noreplypratap.breakingnews.core.utils.showBottomSheetDialog
import com.noreplypratap.breakingnews.core.utils.showToastMessage
import com.noreplypratap.breakingnews.presentation.viewmodels.RemoteNewsViewModel
import com.noreplypratap.breakingnews.presentation.viewmodels.LocalNewsViewModel
import com.noreplypratap.domain.model.NewsArticle
import com.noreplypratap.domain.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    val TAG = "BreakingNewsFragmentTag"

    private lateinit var binding: FragmentBreakingNewsBinding
    private val remoteNewsViewModel: RemoteNewsViewModel by viewModels()
    private val localNewsViewModel: LocalNewsViewModel by viewModels()
    private val articlesAdapter = NewsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreakingNewsBinding.bind(view)
        binding.rvArticles.adapter = articlesAdapter
        recyclerViewOnClickSetup()
        loadArticles()
        refreshListener()
        setupChips()

        val receivedValue = requireActivity().intent.getStringExtra("id")
        logMessage(TAG,"$receivedValue")
    }
    private fun refreshListener() {
        binding.strNewsArticle.setOnRefreshListener {
            if (requireContext().isOnline()) {
                binding.chipsGroup.clearCheck()
                binding.chipIndia.isChecked = true
                remoteNewsViewModel.getBreakingNews(codeIndia)
                binding.strNewsArticle.isRefreshing = false
            } else {
                binding.strNewsArticle.isRefreshing = false
            }
        }
    }
    private fun setupChips() {

        binding.chipUS.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipUS.isChecked = true
            if (binding.chipUS.isChecked) {
                searchByChips(codeUS)
            }
        }

        binding.chipRussia.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipRussia.isChecked = true
            if (binding.chipRussia.isChecked) {
                searchByChips(codeRussia)
            }
        }

        binding.chipIndia.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipIndia.isChecked = true
            if (binding.chipIndia.isChecked) {
                searchByChips(codeIndia)
            }
        }

        binding.chipBrazil.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipBrazil.isChecked = true
            if (binding.chipBrazil.isChecked) {
                searchByChips(codeBrazil)
            }
        }

        binding.chipCanada.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipCanada.isChecked = true
            if (binding.chipCanada.isChecked) {
                searchByChips(codeCanada)
            }
        }

        binding.chipSwitzerland.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipSwitzerland.isChecked = true
            if (binding.chipSwitzerland.isChecked) {
                searchByChips(codeSwitzerland)
            }
        }

    }
    private fun searchByChips(cc: String) {
        if (requireContext().isOnline()) {
            showThisList(null)
            remoteNewsViewModel.getBreakingNews(cc)
        } else {
            requireContext().showToastMessage("No Internet...")
        }
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
            textView.text = "Breaking News"
        }

        if (imageButton != null) {
            imageButton.visibility = View.GONE
        }

    }
    private fun loadArticles() {
        showProgressBar()
        remoteNewsViewModel.getBreakingNews(codeIndia)
        remoteNewsViewModel.remoteArticles.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    data.data?.let {
                        hideProgressBar()
                        showThisList(it)
                    }
                    hideProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    logMessage(TAG, data.message.toString())
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun showThisList(it: List<NewsArticle>?) {
        articlesAdapter.differ.submitList(it)
        articlesAdapter.notifyDataSetChanged()
    }
    private fun recyclerViewOnClickSetup() {
        articlesAdapter.setOnClickListener { article ->
            requireContext().showBottomSheetDialog(article, saveArticle = {
                val list = localNewsViewModel.savedArticles.value
                list?.forEach{ news ->
                    if (news.title == article.title) {
                        localNewsViewModel.updateArticle(article)
                        return@showBottomSheetDialog
                    }
                }
                localNewsViewModel.createArticle(article)
                requireContext().showToastMessage("Saved Article")
            })
        }
    }
    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

}

