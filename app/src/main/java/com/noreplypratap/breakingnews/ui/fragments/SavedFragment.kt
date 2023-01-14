package com.noreplypratap.breakingnews.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.databinding.FragmentSavedBinding
import com.noreplypratap.breakingnews.ui.adapters.BreakingNewsAdapter
import com.noreplypratap.breakingnews.utils.*
import com.noreplypratap.breakingnews.viewmodel.RoomDBViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.fragment_saved) {

    private lateinit var binding: FragmentSavedBinding
    private val roomDBViewModel: RoomDBViewModel by viewModels()
    private lateinit var dialog: BottomSheetDialog

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        val newsAdapter = BreakingNewsAdapter()
        binding.rvSavedNews.adapter = newsAdapter
        roomDBViewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
            newsAdapter.notifyDataSetChanged()
        }

        newsAdapter.setOnClickListener {news ->
            val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
            news.urlToImage?.let { it1 ->
                requireContext().glide(
                    it1,
                    dialogView.findViewById(R.id.imageView)
                )
            }
            dialogView.findViewById<TextView>(R.id.tvTitle).text = news.title.toString()
            if (news.description.isNullOrEmpty()) {
                dialogView.findViewById<TextView>(R.id.tvDesc).text = news.description.toString()
            }
            dialogView.findViewById<TextView>(R.id.tvTime).text = news.publishedAt.toString()
            dialogView.findViewById<Button>(R.id.btnDelete).visibility = View.VISIBLE
            dialogView.findViewById<Button>(R.id.btnDelete).setOnClickListener {
                roomDBViewModel.deleteFavNews(news)
            }
            dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            dialog.setContentView(dialogView)
            dialog.show()
            dialogView.findViewById<Button>(R.id.btnURL).setOnClickListener { _ ->
                if (context?.isOnline() == true) {
                    news.url?.let { it1 -> requireContext().webBuilder(it1) }
                } else {
                    logging("No Internet")
                    requireContext().showToast("No Internet")
                }
            }
        }
    }
}
