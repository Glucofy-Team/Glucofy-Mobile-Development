package com.dicoding2.glucofy.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding2.glucofy.ui.glucose.monthly.GlucosaMonthlyFragment
import com.dicoding2.glucofy.ui.glucose.today.GlucosaTodayFragment
import com.dicoding2.glucofy.ui.glucose.weekly.GlucosaWeeklyFragment

class SectionPagerGlucosaAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = GlucosaTodayFragment()
            1 -> fragment = GlucosaWeeklyFragment()
            2 -> fragment = GlucosaMonthlyFragment()
        }
        return fragment as Fragment
    }

}