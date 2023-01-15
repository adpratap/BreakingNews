package com.noreplypratap.breakingnews.ui.fragments

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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.databinding.FragmentSearchBinding
import com.noreplypratap.breakingnews.ui.adapters.NewsAdapter
import com.noreplypratap.breakingnews.utils.*
import com.noreplypratap.breakingnews.viewmodel.BreakingNewsViewModel
import com.noreplypratap.breakingnews.viewmodel.RoomDBViewModel
import com.noreplypratap.breakingnews.viewmodel.SearchNewsViewModel
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
    private val mainViewModel: BreakingNewsViewModel by viewModels()
    private val roomDBViewModel: RoomDBViewModel by viewModels()
    private lateinit var dialog: BottomSheetDialog


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
            logging("Coming Soon ... ")
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
        mainViewModel.breakingNews.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    data.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                        newsAdapter.notifyDataSetChanged()
                    }
                    hideProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    logging(data.message.toString())
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun setupChips() {
        binding.chipBusiness.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipBusiness.isChecked = true
            if (binding.chipBusiness.isChecked) {
                mainViewModel.getBreakingNews("", "business", "")
            }
        }
        binding.chipEntertainment.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipEntertainment.isChecked = true
            if (binding.chipEntertainment.isChecked) {
                mainViewModel.getBreakingNews("", "entertainment", "")
            }
        }
        binding.chipGeneral.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipGeneral.isChecked = true
            if (binding.chipGeneral.isChecked) {
                mainViewModel.getBreakingNews("", "general", "")
            }
        }
        binding.chipHealth.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipHealth.isChecked = true
            if (binding.chipHealth.isChecked) {
                mainViewModel.getBreakingNews("", "health", "")
            }
        }
        binding.chipScience.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipScience.isChecked = true
            if (binding.chipScience.isChecked) {
                mainViewModel.getBreakingNews("", "science", "")
            }
        }
        binding.chipSports.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipSports.isChecked = true
            if (binding.chipSports.isChecked) {
                mainViewModel.getBreakingNews("", "sports", "")
            }
        }
        binding.chipTechnology.setOnClickListener {
            binding.chipsCategoryGroup.clearCheck()
            binding.chipTechnology.isChecked = true
            if (binding.chipTechnology.isChecked) {
                mainViewModel.getBreakingNews("", "technology", "")
            }
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
        adapter.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
            it.urlToImage?.let { it1 ->
                requireContext().glide(
                    it1,
                    dialogView.findViewById(R.id.imageView)
                )
            }
            dialogView.findViewById<TextView>(R.id.tvTitle).text = it.title.toString()
            dialogView.findViewById<TextView>(R.id.tvDesc).text = it.description.toString()
            dialogView.findViewById<TextView>(R.id.tvTime).text = it.publishedAt.toString()
            dialogView.findViewById<Button>(R.id.btnURL).setOnClickListener { _ ->
                if (context?.isOnline() == true) {
                    it.url?.let { it1 -> requireContext().webBuilder(it1) }
                } else {
                    logging("No Internet")
                    requireContext().showToast("No Internet")
                }
            }
            dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            dialog.setContentView(dialogView)
            dialog.show()

            roomDBViewModel.saveFavNews(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObserver(adapter: NewsAdapter) {
        searchViewModel.searchNews.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    data.data?.let {
                        adapter.differ.submitList(it.articles)
                        adapter.notifyDataSetChanged()
                    }
                    hideProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    logging(data.message.toString())
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
            context?.showToast("Empty text ..... ")
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

