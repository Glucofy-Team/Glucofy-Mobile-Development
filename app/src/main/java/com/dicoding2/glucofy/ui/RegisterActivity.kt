package com.dicoding2.glucofy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.adapter.SectionPagerAdapter
import com.dicoding2.glucofy.data.RegisterPreference
import com.dicoding2.glucofy.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.isUserInputEnabled = false
        supportActionBar?.elevation = 0f

        binding.tvBack.setOnClickListener {
            viewPager.setCurrentItem(0,true)
        }
        binding.tvNext.setOnClickListener {  }
    }

    override fun onStart() {
        super.onStart()
        deleteUserRegister()
    }

    fun deleteUserRegister() {
        val registerPreference = RegisterPreference(this)
        registerPreference.deleteUserRegister()
    }
}