package com.dicoding2.glucofy.ui.food

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.databinding.ActivityFoodDetailBinding
import com.dicoding2.glucofy.ui.factory.ViewModelFactory

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailBinding
    private lateinit var viewModel : FoodDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this)

        val foodName = intent.getStringExtra("foodName")
        val calories = intent.getIntExtra("calories", 0)
        val fats = intent.getDoubleExtra("fats", 0.0)
        val carbs = intent.getDoubleExtra("carbs", 0.0)
        val proteins = intent.getDoubleExtra("proteins", 0.0)
        val gIndex = intent.getIntExtra("gIndex", 0)
        val gLoad = intent.getDoubleExtra("gLoad", 0.0)
        val category = intent.getStringExtra("category")

        val food = FoodListItem(
            foodName = foodName,
            calories = calories,
            fats = fats,
            carbs = carbs,
            proteins = proteins,
            gIndex = gIndex,
            gLoad = gLoad,
            category = category,
            giCategory = "",
            glCategory = "",
            id = "",
        )

        showFoodDetails(food)
    }
    private fun showFoodDetails(food: FoodListItem) {
        with(binding) {
            tvFoodName.text = food.foodName
            tvFoodCategory.text = food.category
            tvGlIndex.text = food.gIndex.toString()
            tvProtein.text = food.proteins.toString()
            tvFats.text = food.fats.toString()
            tvGlLoad.text = food.gLoad.toString()
            tvCalories.text = food.calories.toString()
            tvCarbohydrate.text = food.carbs.toString()
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FoodDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FoodDetailViewModel::class.java]
    }
}