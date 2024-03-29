package com.noreplypratap.breakingnews.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.databinding.FragmentSearchBinding
import com.noreplypratap.breakingnews.presentation.adapters.NewsAdapter
import com.noreplypratap.breakingnews.core.utils.isOnline
import com.noreplypratap.breakingnews.core.utils.showBottomSheetDialog
import com.noreplypratap.breakingnews.core.utils.Constants.TAG
import com.noreplypratap.breakingnews.core.utils.logMessage
import com.noreplypratap.breakingnews.core.utils.showToastMessage
import com.noreplypratap.breakingnews.presentation.viewmodels.RemoteNewsViewModel
import com.noreplypratap.breakingnews.presentation.viewmodels.LocalNewsViewModel
import com.noreplypratap.breakingnews.presentation.viewmodels.SearchNewsViewModel
import com.noreplypratap.domain.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class SearchNewsFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchNewsViewModel by viewModels()
    private val remoteNewsViewModel: RemoteNewsViewModel by viewModels()
    private val localNewsViewModel: LocalNewsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        val newsAdapter = NewsAdapter()
        binding.rvSearchFragment.adapter = newsAdapter
        recyclerViewOnClick(newsAdapter)

        setObserver(newsAdapter)
        mainViewModelObs(newsAdapter)
        appBarSetup(newsAdapter)
        setupChips()

    }

    private fun appBarSetup(newsAdapter: NewsAdapter) {
        val textView = activity?.findViewById<TextView>(R.id.tvAppBar)
        val searchView = activity?.findViewById<SearchView>(R.id.svAppBarSearch)
        val imageButton = activity?.findViewById<ImageButton>(R.id.ibFilterBtn)
        if (searchView != null) {
            searchView.visibility = View.VISIBLE
            searchView.requestFocus()
            searchView.isIconified = false
            val inputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
            svSearch(searchView, newsAdapter)
        }

        imageButton?.setOnClickListener {
            logMessage(TAG, "Coming Soon ... ")
        }

        if (textView != null) {
            textView.visibility = View.GONE
        }

        if (imageButton != null) {
            imageButton.visibility = View.VISIBLE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun mainViewModelObs(newsAdapter: NewsAdapter) {
        remoteNewsViewModel.remoteArticles.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    data.data?.let {
                        newsAdapter.differ.submitList(it)
                        newsAdapter.notifyDataSetChanged()
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

                else -> {
                    logMessage(TAG, "data.message.toString()")
                    hideProgressBar()
                }
            }
        }
    }

    private fun setupChips() {
        binding.chipBusiness.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipBusiness.isChecked = true
            if (binding.chipBusiness.isChecked) {
                apiCall("business")
            }
        }
        binding.chipEntertainment.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipEntertainment.isChecked = true
            if (binding.chipEntertainment.isChecked) {
                apiCall("entertainment")
            }
        }
        binding.chipGeneral.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipGeneral.isChecked = true
            if (binding.chipGeneral.isChecked) {
                apiCall("general")
            }
        }
        binding.chipHealth.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipHealth.isChecked = true
            if (binding.chipHealth.isChecked) {
                apiCall("health")
            }
        }
        binding.chipScience.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipScience.isChecked = true
            if (binding.chipScience.isChecked) {
                apiCall("science")
            }
        }
        binding.chipSports.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipSports.isChecked = true
            if (binding.chipSports.isChecked) {
                apiCall("sports")
            }
        }
        binding.chipTechnology.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipTechnology.isChecked = true
            if (binding.chipTechnology.isChecked) {
                apiCall("technology")
            }
        }
    }

    private fun apiCall(category: String) {
        if (context?.isOnline() == true) {
            remoteNewsViewModel.getBreakingNews("", category, "")
        } else {
            requireContext().showToastMessage("No Internet...")
        }
    }

    private fun svSearch(searchView: SearchView, newsAdapter: NewsAdapter) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchNews(query, newsAdapter)
                return true
            }

            var job: Job? = null
            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    newText.let {
                        Log.d("onQueryTextChange...", newText.toString())
                        searchNews(newText, newsAdapter)
                    }
                }
                return true
            }

        })
    }

    override fun onResume() {
        super.onResume()
        val textView = activity?.findViewById<TextView>(R.id.tvAppBar)
        val searchView = activity?.findViewById<SearchView>(R.id.svAppBarSearch)
        val imageButton = activity?.findViewById<ImageButton>(R.id.ibFilterBtn)
        if (searchView != null) {
            searchView.visibility = View.VISIBLE
            searchView.requestFocus()
            searchView.isIconified = false
        }

        if (textView != null) {
            textView.visibility = View.GONE
        }

        if (imageButton != null) {
            imageButton.visibility = View.VISIBLE
        }
    }

    @SuppressLint("InflateParams")
    private fun recyclerViewOnClick(adapter: NewsAdapter) {
        adapter.setOnClickListener { article ->
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



    @SuppressLint("NotifyDataSetChanged")
    private fun setObserver(adapter: NewsAdapter) {
        searchViewModel.searchNewsArticles.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    data.data?.let {
                        adapter.differ.submitList(it)
                        adapter.notifyDataSetChanged()
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
    private fun searchNews(sText: String?, adapter: NewsAdapter) {
        if (sText!!.isNotEmpty() && sText != "" && context?.isOnline() == true) {
            searchViewModel.searchNews(sText)
            binding.chipsCategoryGroup.clearCheck()
        } else {
            context?.showToastMessage("Error ..... ")
            adapter.differ.submitList(null)
            adapter.notifyDataSetChanged()
        }
    }

    private fun hideProgressBar() {
        binding.SearchNewsProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.SearchNewsProgressBar.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        val searchView = activity?.findViewById<SearchView>(R.id.svAppBarSearch)
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (searchView != null) {
            inputMethodManager.hideSoftInputFromWindow(searchView.windowToken, 0)
            searchView.clearFocus()
        }
    }

}

