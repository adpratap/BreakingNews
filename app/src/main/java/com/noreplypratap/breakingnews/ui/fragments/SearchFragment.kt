package com.noreplypratap.breakingnews.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.adapters.NewsAdapter
import com.noreplypratap.breakingnews.databinding.FragmentSearchBinding
import com.noreplypratap.breakingnews.utils.Constants
import com.noreplypratap.breakingnews.utils.Resource
import com.noreplypratap.breakingnews.utils.isOnline
import com.noreplypratap.breakingnews.viewmodel.APIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    val mainViewModel : APIViewModel by viewModels()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var job : Job? = null
        binding.etSearchFragment.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let {
                    if (it.toString().isNotEmpty() && context?.isOnline() == true){
                        mainViewModel.searchNews(it.toString())

                    }else{
                        newsAdapter.differ.submitList(null)
                    }
                }
            }
        }

        setRecyclerView()
        mainViewModel.searchNews.observe(viewLifecycleOwner, Observer { data ->
            when(data){
                is Resource.Success -> {
                    hideProgressBar()
                    data.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        val totalPages = it.totalResults / 22
                        isLastPage = mainViewModel.searchNewsPage == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    data.message?.let {
                        Log.d(Constants.TAG,it)
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

    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    val scrollListner = object : RecyclerView.OnScrollListener(){
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
                mainViewModel.searchNews(binding.etSearchFragment.text.toString())
                isScrolling = false
            }
        }
    }

    private fun setRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.rvSearchFragment.apply {
            adapter = newsAdapter
            layoutManager = GridLayoutManager(activity,2)
            addOnScrollListener(this@SearchFragment.scrollListner)
        }

        newsAdapter.setOnClickListener {
            if (context?.isOnline() == true){
                val customTabsIntent = CustomTabsIntent.Builder().build()
                context?.let { it1 -> customTabsIntent.launchUrl(it1,Uri.parse(it.url) )}
            }else{
                Log.d(Constants.TAG,"No Internet")
            }
        }
    }

}