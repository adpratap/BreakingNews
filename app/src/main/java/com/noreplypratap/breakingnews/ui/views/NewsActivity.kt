package com.noreplypratap.breakingnews.ui.views

import android.content.res.Resources.NotFoundException
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.noreplypratap.breakingnews.BreakingNewsApplication
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.databinding.ActivityNewsBinding
import com.noreplypratap.breakingnews.ui.adapters.ViewPagerAdapter
import com.noreplypratap.breakingnews.utils.logMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class NewsActivity : AppCompatActivity(){

    val TAG = "NewsActivityTag"

    private var _binding:  ActivityNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_news)

        //Request Permissions...
        val breakingNewsApplication = application as BreakingNewsApplication
        breakingNewsApplication.requestPermissions(this)

        //Setup View Pager...
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, pos ->
            tab.text = when(pos){
                0 -> {"News"}
                1 -> {"Search"}
                2 -> {"Saved"}
                else -> {
                    throw NotFoundException("Error poss")
                }
            }
        }.attach()

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}