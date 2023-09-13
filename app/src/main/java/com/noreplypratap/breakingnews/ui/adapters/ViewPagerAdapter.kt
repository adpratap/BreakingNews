package com.noreplypratap.breakingnews.ui.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.noreplypratap.breakingnews.ui.fragments.BreakingNewsFragment
import com.noreplypratap.breakingnews.ui.fragments.SavedFragment
import com.noreplypratap.breakingnews.ui.fragments.SearchNewsFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {BreakingNewsFragment()}
            1 -> {SearchNewsFragment()}
            2 -> {SavedFragment()}
            else -> {
                throw Resources.NotFoundException("Position Error")
            }
        }
    }
}