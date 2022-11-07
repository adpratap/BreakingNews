package com.noreplypratap.breakingnews.ui.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.databinding.ActivityWebBinding
import com.noreplypratap.breakingnews.utils.Constants
import com.noreplypratap.breakingnews.utils.TempData.isOnline
import com.noreplypratap.breakingnews.utils.TempData.onClickNewsUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebActivity : AppCompatActivity() {

    lateinit var binding : ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_web)

        if (isOnline(this)){
            Log.d(Constants.TAG,"true....................")
            binding.fragmentContainerView.visibility = View.GONE
            binding.webView.visibility = View.VISIBLE
            onClickNewsUrl?.let { binding.webView.loadUrl(it) }
        }else{
            Log.d(Constants.TAG,"false....................")
            binding.webView.visibility = View.GONE
            binding.fragmentContainerView.visibility = View.VISIBLE
        }
    }
}