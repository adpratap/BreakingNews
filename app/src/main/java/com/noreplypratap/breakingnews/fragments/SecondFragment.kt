package com.noreplypratap.breakingnews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.noreplypratap.breakingnews.databinding.FragmentSecondBinding
import com.noreplypratap.breakingnews.utils.Constants.nextNewsForFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextNewsForFragment?.url?.let { binding.wvNews.loadUrl(it) }

        //Glide.with(this).load(nextNewsForFragment?.urlToImage).into(binding.ivSNewsImage)

//        binding.tvSNewsHeading.text = nextNewsForFragment?.title
//        binding.tvSNewsCon.text = nextNewsForFragment?.content
//        binding.tvSNewsBody.text = nextNewsForFragment?.description
//        binding.tvSNewsTime.text = nextNewsForFragment?.publishedAt
//        binding.tvSNewsAuth.text = nextNewsForFragment?.author
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}