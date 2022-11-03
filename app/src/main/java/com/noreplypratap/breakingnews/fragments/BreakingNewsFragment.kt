package com.noreplypratap.breakingnews.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.databinding.FragmentBreakingNewsBinding
import com.noreplypratap.breakingnews.utils.Constants.LOG_TAG
import com.noreplypratap.breakingnews.utils.Constants.nextNewsDatas
import com.noreplypratap.breakingnews.utils.Constants.nextNewsForFragment
import com.noreplypratap.breakingnews.utils.CustomAdapter
import com.noreplypratap.breakingnews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

    private lateinit var binding: FragmentBreakingNewsBinding
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(isOnline(context)){

            if(nextNewsDatas?.status != "ok"){

                mainViewModel.getBreakingNewsData()

                mainViewModel.responseLiveNewsData.observe(viewLifecycleOwner) {

                    Log.d(LOG_TAG, it.toString())

                    nextNewsDatas = it

                    setNewstoView()

                }

            }else{
                setNewstoView()
            }
        }


    }

    fun isOnline(context: Context?): Boolean {

        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }

    private fun setNewstoView() {

        val customAdapter = nextNewsDatas?.articles?.let { CustomAdapter(it) }

        binding.rvNewsView.adapter = customAdapter
        binding.rvNewsView.layoutManager = LinearLayoutManager(context)

        if (customAdapter != null) {
            customAdapter.onItemClick = { news ->
                nextNewsForFragment = news
                Log.d(LOG_TAG,"ItemClicked ${news.title}")
                if (isOnline(context)){
                    findNavController().navigate(R.id.action_breakingNewsFragment_to_secondFragment)
                }

            }
        }

    }

}