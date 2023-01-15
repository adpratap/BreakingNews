package com.noreplypratap.breakingnews.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.databinding.FragmentBreakingNewsBinding
import com.noreplypratap.breakingnews.ui.adapters.NewsAdapter
import com.noreplypratap.breakingnews.utils.*
import com.noreplypratap.breakingnews.viewmodel.BreakingNewsViewModel
import com.noreplypratap.breakingnews.viewmodel.RoomDBViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var binding: FragmentBreakingNewsBinding
    private val mainViewModel: BreakingNewsViewModel by viewModels()
    private val roomDBViewModel: RoomDBViewModel by viewModels()
    private lateinit var dialog: BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreakingNewsBinding.bind(view)

        val newsAdapter = NewsAdapter()
        binding.rvNewsView.adapter = newsAdapter
        recyclerViewOnClick(newsAdapter)
        subscribeUI(newsAdapter)
        setupChips(newsAdapter)
        setupCall()

        //hitAPI(newsAdapter)

        binding.strNewsArticle.setOnRefreshListener {
            setupCall()
        }
    }

    private fun setupCall() {
        if (context?.isOnline() == true) {
            binding.chipsGroup.clearCheck()
            binding.chipIndia.isChecked = true
            mainViewModel.getBreakingNews(codeIndia, "", "")
            binding.strNewsArticle.isRefreshing = false
        } else {
            requireContext().showToast("No Internet")
            binding.strNewsArticle.isRefreshing = false
        }
    }

    private fun setupChips(newsAdapter: NewsAdapter) {

        binding.chipUS.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipUS.isChecked = true
            if (binding.chipUS.isChecked) {
                searchByChips(newsAdapter, codeUS)
            }
        }

        binding.chipRussia.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipRussia.isChecked = true
            if (binding.chipRussia.isChecked) {
                searchByChips(newsAdapter, codeRussia)
            }
        }

        binding.chipIndia.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipIndia.isChecked = true
            if (binding.chipIndia.isChecked) {
                searchByChips(newsAdapter, codeIndia)
            }
        }

        binding.chipBrazil.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipBrazil.isChecked = true
            if (binding.chipBrazil.isChecked) {
                searchByChips(newsAdapter, codeBrazil)
            }
        }

        binding.chipCanada.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipCanada.isChecked = true
            if (binding.chipCanada.isChecked) {
                searchByChips(newsAdapter, codeCanada)
            }
        }

        binding.chipSwitzerland.setOnClickListener {
            binding.chipsGroup.clearCheck()
            binding.chipSwitzerland.isChecked = true
            if (binding.chipSwitzerland.isChecked) {
                searchByChips(newsAdapter, codeSwitzerland)
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchByChips(newsAdapter: NewsAdapter, codeUS: String) {
        if (context?.isOnline() == true) {
            newsAdapter.differ.submitList(null)
            newsAdapter.notifyDataSetChanged()
            mainViewModel.getBreakingNews(codeUS, "", "")
        } else {
            requireContext().showToast("No Internet...")
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

    private fun hitAPI(adapter: NewsAdapter) {
        binding.chipIndia.isChecked = true
        if (context?.isOnline() == true) {
            subscribeUI(adapter)
        } else {
            requireContext().showToast("No Internet")
        }

    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun offlineData(adapter: NewsAdapter) {
        roomDBViewModel.getSavedNews().observe(viewLifecycleOwner) {
            adapter.differ.submitList(it)
            adapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun subscribeUI(adapter: NewsAdapter) {
        showProgressBar()
        //mainViewModel.getBreakingNews(codeIndia, "", "")
        mainViewModel.breakingNews.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    data.data?.let {

                        //Set to UI
                        adapter.differ.submitList(it.articles)
                        adapter.notifyDataSetChanged()

                        //Save To DB
                        //roomDBViewModel.saveNews(it.articles)
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

    @SuppressLint("InflateParams")
    private fun recyclerViewOnClick(newsAdapter: NewsAdapter) {
        newsAdapter.setOnClickListener { news ->
            val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
            news.urlToImage?.let { it1 ->
                requireContext().glide(
                    it1,
                    dialogView.findViewById(R.id.imageView)
                )
            }
            dialogView.findViewById<TextView>(R.id.tvTitle).text = news.title.toString()
            dialogView.findViewById<TextView>(R.id.tvDesc).text = news.description.toString()
            dialogView.findViewById<TextView>(R.id.tvTime).text = news.publishedAt.toString()
            dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            dialog.setContentView(dialogView)
            dialog.show()
            dialogView.findViewById<Button>(R.id.btnURL).setOnClickListener {
                if (context?.isOnline() == true) {
                    news.url?.let { it1 -> requireContext().webBuilder(it1) }
                } else {
                    logging("No Internet")
                    requireContext().showToast("No Internet")
                }
            }
            //Save to DB
            roomDBViewModel.saveFavNews(news)
        }
    }

}

