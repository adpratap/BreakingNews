package com.noreplypratap.breakingnews.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noreplypratap.breakingnews.adapters.NewsAdapter
import com.noreplypratap.breakingnews.databinding.FragmentBreakingNewsBinding
import com.noreplypratap.breakingnews.ui.views.WebActivity
import com.noreplypratap.breakingnews.utils.Constants.TAG
import com.noreplypratap.breakingnews.utils.Resource
import com.noreplypratap.breakingnews.utils.TempData.isOnline
import com.noreplypratap.breakingnews.utils.TempData.onClickNewsUrl
import com.noreplypratap.breakingnews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

    private lateinit var binding: FragmentBreakingNewsBinding
    private val mainViewModel: MainViewModel by viewModels()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(context?.let { isOnline(it) } == true){
            setupPage()
        }else{
            startActivity(Intent(context,WebActivity::class.java))
        }

    }

    private fun setupPage() {
        setRecyclerView()
        newsAdapter.setOnClickListener {
            Log.d(TAG,"clicked....................")
            onClickNewsUrl = it.url
            startActivity(Intent(context,WebActivity::class.java))
        }

        mainViewModel.getBreakingNews("in")
        mainViewModel.breakingNews.observe(viewLifecycleOwner, Observer { data ->

            when(data){
                is Resource.Success -> {
                    hideProgressBar()
                    data.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
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

            if (shouldPaginate && context?.let { isOnline(it) } == true){
                mainViewModel.getBreakingNews("in")
                isScrolling = false
            }
        }
    }
    private fun setRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.rvNewsView.apply {
            adapter = newsAdapter
            layoutManager = GridLayoutManager(activity,2)
            addOnScrollListener(this@BreakingNewsFragment.scrollListner)
        }
    }

}

