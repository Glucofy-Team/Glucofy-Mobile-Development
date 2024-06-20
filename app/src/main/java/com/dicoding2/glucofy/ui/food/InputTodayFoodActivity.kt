package com.dicoding2.glucofy.ui.food

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.databinding.ActivityInputTodayFoodBinding
import com.dicoding2.glucofy.ui.factory.ViewModelFactory

class InputTodayFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputTodayFoodBinding
    private lateinit var viewModel : FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInputTodayFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        viewModel = obtainViewModel(this)

        val fragment = ExploreFoodFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentExploreFood, fragment)
            .commit()

    }

    private fun obtainViewModel(activity: AppCompatActivity): FoodViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FoodViewModel::class.java]
    }
}