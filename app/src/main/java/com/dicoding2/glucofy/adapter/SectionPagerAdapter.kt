package com.dicoding2.glucofy.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding2.glucofy.ui.auth.RegisterFragmentStep1
import com.dicoding2.glucofy.ui.auth.RegisterFragmentStep2

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = RegisterFragmentStep1()
            1 -> fragment = RegisterFragmentStep2()
        }
        return fragment as Fragment
    }

}