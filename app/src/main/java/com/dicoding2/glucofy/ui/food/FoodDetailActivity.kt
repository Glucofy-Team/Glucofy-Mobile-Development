package com.dicoding2.glucofy.ui.food

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.databinding.ActivityFoodDetailBinding
import com.dicoding2.glucofy.helper.toast
import com.dicoding2.glucofy.ui.factory.ViewModelFactory

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailBinding
    private lateinit var viewModel : FoodViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this)

        val foodName = intent.getStringExtra("foodName")
        val calories = intent.getDoubleExtra("calories", 0.0)
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

        binding.btnSave.setOnClickListener {
            viewModel.saveFood(foodName.toString(), calories.toString(), fats.toString(), carbs.toString(), proteins.toString(), gIndex.toString(), gLoad.toString(), category.toString()).observe(this){ result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.btnSave.isEnabled = false
                        }
                        is Result.Success -> {
                            binding.btnSave.isEnabled = true
                            toast(this, "Berhasil menambahkan data")
                            finish()
                        }
                        is Result.Error -> {

                        }
                    }
                }
            }
        }

        showFoodDetails(food)
    }
    private fun showFoodDetails(food: FoodListItem) {
        with(binding) {
            tvFoodName.text = food.foodName
            tvFoodCategory.text = food.category
            tvGlIndex.text = food.gIndex.toString()
            tvProtein.text = "${ food.proteins }g"
            tvFats.text = "${ food.fats }g"
            tvGlLoad.text = food.gLoad.toString()
            tvCalories.text = "${ food.calories }kkal"
            tvCarbohydrate.text = "${ food.carbs }g"
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FoodViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FoodViewModel::class.java]
    }
}