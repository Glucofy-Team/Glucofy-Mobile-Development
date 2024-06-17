package com.dicoding2.glucofy.ui.food

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.data.remote.response.DetailFood
import com.dicoding2.glucofy.databinding.ActivityFoodDetailBinding
import com.dicoding2.glucofy.ui.viewmodel.ViewModelFactory

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailBinding
    private lateinit var viewModel : FoodDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this)

        val foodId = intent.getStringExtra("id")

        foodId?.let {
            viewModel.getFoodDetail(it)
        }

        viewModel.food.observe(this) { foodResponse ->
            if (foodResponse!=null) {
                showFoodDetails(foodResponse.detailFood)
            } else {
                Toast.makeText(this, "Failed to load food", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showFoodDetails(food: DetailFood) {
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