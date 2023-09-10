package com.noreplypratap.breakingnews.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.databinding.FragmentSavedBinding
import com.noreplypratap.breakingnews.network.isOnline
import com.noreplypratap.breakingnews.ui.adapters.NewsAdapter
import com.noreplypratap.breakingnews.utils.*
import com.noreplypratap.breakingnews.viewmodel.RoomDBViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.fragment_saved) {

    private lateinit var binding: FragmentSavedBinding
    private val roomDBViewModel: RoomDBViewModel by viewModels()
    private lateinit var dialog: BottomSheetDialog

    @SuppressLint("NotifyDataSetChanged", "InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        val newsAdapter = NewsAdapter()
        binding.rvSavedNews.adapter = newsAdapter
        roomDBViewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
            newsAdapter.notifyDataSetChanged()
        }

        newsAdapter.setOnClickListener { news ->
            val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
            val newsUrl: String? = news.urlToImage
            if (!newsUrl.isNullOrBlank()){
                requireContext().glide(
                    newsUrl,
                    dialogView.findViewById(R.id.ivFNewsImage)
                )
            }else {
                dialogView.findViewById<ImageView>(R.id.ivFNewsImage).setImageResource(R.drawable.launcher_new)
            }
            dialogView.findViewById<TextView>(R.id.tvFNewsHeading).text = news.title.toString()
            dialogView.findViewById<TextView>(R.id.tvFNewsBody).apply {
                visibility = View.VISIBLE
                text = news.description.toString()
            }
            //dialogView.findViewById<TextView>(R.id.tvTime).text = news.publishedAt.toString()
            dialogView.findViewById<Button>(R.id.btnDelete).visibility = View.VISIBLE
            dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            dialog.setContentView(dialogView)
            dialog.show()

            dialogView.findViewById<Button>(R.id.btnDelete).setOnClickListener {
                roomDBViewModel.deleteFavNews(news)
                dialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.btnURL).setOnClickListener {
                if (context?.isOnline() == true) {
                    news.url?.let { it1 -> requireContext().webBuilder(it1) }
                } else {
                    logging("No Internet")
                    requireContext().showToast("No Internet")
                }
            }
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
            textView.text = "Saved News"
        }

        if (imageButton != null) {
            imageButton.visibility = View.GONE
        }

    }
}
