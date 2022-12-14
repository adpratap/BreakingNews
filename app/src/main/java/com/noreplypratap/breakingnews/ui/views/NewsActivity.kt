package com.noreplypratap.breakingnews.ui.views

import android.content.res.Resources.NotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.ui.adapters.ViewPagerAdapter
import com.noreplypratap.breakingnews.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_news)
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab,pos ->
            tab.text = when(pos){
                0 -> {"News"}
                1 -> {"Search"}
                else -> {
                    throw NotFoundException("Error poss")
                }
            }
        }.attach()
    }
}