package com.noreplypratap.breakingnews.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.fragments.BreakingNewsFragment
import com.noreplypratap.breakingnews.fragments.NoNetFragment
import com.noreplypratap.breakingnews.fragments.TestFragment
import com.noreplypratap.breakingnews.utils.Constants.netStatus
import com.noreplypratap.breakingnews.utils.NetworkConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var networkConnection : NetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this) { isConnected ->
            if (isConnected) {
                netStatus = true
                if (savedInstanceState == null) {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        //replace<TestFragment>(R.id.fragmentContainerView)
                        replace<BreakingNewsFragment>(R.id.fragmentContainerView)
                    }
                }
                Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
            } else {
                netStatus = false
                if (savedInstanceState == null) {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<NoNetFragment>(R.id.fragmentContainerView)
                    }
                }
                Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show()
            }
        }

    }

}