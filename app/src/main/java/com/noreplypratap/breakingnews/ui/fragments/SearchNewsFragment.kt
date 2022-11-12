package com.noreplypratap.breakingnews.ui.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.databinding.FragmentSearchBinding
import com.noreplypratap.breakingnews.ui.adapters.SearchNewsAdapter
import com.noreplypratap.breakingnews.utils.Constants
import com.noreplypratap.breakingnews.utils.Resource
import com.noreplypratap.breakingnews.utils.isOnline
import com.noreplypratap.breakingnews.viewmodel.SearchNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val mainViewModel: SearchNewsViewModel by viewModels()
    private lateinit var searchNewsAdapter: SearchNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        setObserver()
        if (context?.isOnline() == true) {
            searchNews()

        }
    }

    private fun setObserver() {
        mainViewModel.searchNews.observe(viewLifecycleOwner, Observer { data ->
            when (data) {
                is Resource.Success -> {
                    hideProgressBar()
                    data.data?.let {
                        searchNewsAdapter = SearchNewsAdapter(it.articles)
                        setRecyclerView()
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    data.message?.let {
                        Log.d(Constants.TAG, it)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchNews() {
        var job: Job? = null
        binding.etSearchFragment.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let {
                    if (it.toString().isNotEmpty() && context?.isOnline() == true) {
                        mainViewModel.searchNews(it.toString())
                    } else {
                        if (context?.isOnline() == true) {
                            searchNewsAdapter.articles.clear()
                            searchNewsAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {

    }

    private fun showProgressBar() {

    }

    private fun setRecyclerView() {
        binding.rvSearchFragment.apply {
            adapter = searchNewsAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }

        searchNewsAdapter.setOnClickListener {
            if (context?.isOnline() == true) {
                val customTabsIntent = CustomTabsIntent.Builder().build()
                context?.let { it1 -> customTabsIntent.launchUrl(it1, Uri.parse(it.url)) }
            } else {
                Log.d(Constants.TAG, "No Internet")
            }
        }
    }

}
