package com.dicoding2.glucofy.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding2.glucofy.ui.food.ExploreFoodFragment

class SectionPagerFoodAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ExploreFoodFragment()
            1 -> fragment = ExploreFoodFragment()
        }
        return fragment as Fragment
    }
}