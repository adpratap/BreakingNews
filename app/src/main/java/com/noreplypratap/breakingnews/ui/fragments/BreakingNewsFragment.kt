package com.noreplypratap.breakingnews.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.ui.adapters.BreakingNewsAdapter
import com.noreplypratap.breakingnews.databinding.FragmentBreakingNewsBinding
import com.noreplypratap.breakingnews.utils.Constants.TAG
import com.noreplypratap.breakingnews.utils.Resource
import com.noreplypratap.breakingnews.utils.isOnline
import com.noreplypratap.breakingnews.viewmodel.BreakingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var binding: FragmentBreakingNewsBinding
    private val mainViewModel : BreakingNewsViewModel by viewModels()
    private lateinit var newsAdapter: BreakingNewsAdapter
    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    private val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val visibleItemCount = layoutManager.childCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThenVisible = totalItemCount >= 20
            val shouldPaginate = isNotLoadingAndLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThenVisible && isScrolling

            if (shouldPaginate && context?.isOnline() == true){
                apiCall()
                isScrolling = false
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreakingNewsBinding.bind(view)

        if(context?.isOnline() == true){
            Log.d(TAG,"Load From API...")
            apiCall()
            setupPage()
        }else{
            Log.d(TAG,"Load From DB...")
            offlineData()
        }
    }

    private fun offlineData() {
        mainViewModel.getSavedBreakingNews().observe(viewLifecycleOwner, Observer {
            setRecyclerView()
            newsAdapter.differ.submitList(it.toList())
        })
    }

    private fun apiCall() {
        mainViewModel.getBreakingNews("in")
    }

    private fun setupPage() {
        setRecyclerView()
        mainViewModel.breakingNews.observe(viewLifecycleOwner, Observer { data ->
            when(data){
                is Resource.Success -> {
                    hideProgressBar()
                    data.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        mainViewModel.saveNews(it.articles)
                        Log.d(TAG,"Saved To DB ...............................")
                        val totalPages = it.totalResults / 22
                        isLastPage = mainViewModel.breakingNewsPage == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    data.message?.let {
                        Log.d(TAG,it)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun hideProgressBar() {

    }

    private fun showProgressBar() {

    }

    private fun setRecyclerView(){
        newsAdapter = BreakingNewsAdapter()
        binding.rvNewsView.apply {
            adapter = newsAdapter
            layoutManager = GridLayoutManager(activity,2)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
        newsAdapter.setOnClickListener {
            if (context?.isOnline() == true){
                val customTabsIntent = CustomTabsIntent.Builder().build()
                context?.let { it1 -> customTabsIntent.launchUrl(it1,Uri.parse(it.url) )}
            }else{
                Log.d(TAG,"No Internet")
                Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show()
            }
        }
    }

}

