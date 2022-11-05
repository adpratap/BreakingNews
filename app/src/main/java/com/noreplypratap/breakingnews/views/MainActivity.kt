package com.noreplypratap.breakingnews.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.fragments.BreakingNewsFragment
import com.noreplypratap.breakingnews.fragments.NoNetFragment
import com.noreplypratap.breakingnews.utils.ConnectivityObserver
import com.noreplypratap.breakingnews.utils.Constants.netStatus
import com.noreplypratap.breakingnews.utils.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val connectivityObserver : NetworkConnectivityObserver by lazy {
        NetworkConnectivityObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        netStatus = false
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<NoNetFragment>(R.id.fragmentContainerView)
            }
        }

        lifecycleScope.launch {
            connectivityObserver.observe().collect {
                when (it) {
                    ConnectivityObserver.Status.Available -> {
                        supportFragmentManager.commit {
                            netStatus = true
                            setReorderingAllowed(true)
                            replace<BreakingNewsFragment>(R.id.fragmentContainerView)
                        }
                        Toast.makeText(this@MainActivity,"Network Available",Toast.LENGTH_LONG).show()
                    }
                    ConnectivityObserver.Status.Unavailable -> {
                        netStatus = false
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<NoNetFragment>(R.id.fragmentContainerView)
                        }
                        Toast.makeText(this@MainActivity,"Network Unavailable",Toast.LENGTH_LONG).show()
                    }
                    ConnectivityObserver.Status.Losing -> {
                        netStatus = false
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<NoNetFragment>(R.id.fragmentContainerView)
                        }
                        Toast.makeText(this@MainActivity,"Network Losing",Toast.LENGTH_LONG).show()
                    }
                    ConnectivityObserver.Status.Lost -> {
                        netStatus = false
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<NoNetFragment>(R.id.fragmentContainerView)
                        }
                        Toast.makeText(this@MainActivity,"Network Lost",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}