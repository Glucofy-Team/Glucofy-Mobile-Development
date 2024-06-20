package com.dicoding2.glucofy.ui.food

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.databinding.ActivityInputNewFoodBinding
import com.google.android.material.snackbar.Snackbar

class InputNewFoodActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInputNewFoodBinding
    private lateinit var viewModel : InputNewFoodViewModel

    val category = arrayOf("Sayuran", "Makanan Manis", "Rempah-rempah", "Sup", "Seafood", "Minyak dan saus", "Kacang-kacangan", "Jamur", "Daging", "Biji-bijian", "Buah-buahan", "Fast foods", "Dairy", "Minuman", "Kue dan roti")
    private lateinit var categoryAdapter : ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputNewFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodName = intent.getStringExtra("name")
        binding.edFoodName.text?.append(foodName)

        viewModel = InputNewFoodViewModel.getInstance(this)
        setupCategoryAdapter()

        viewModel.isLoading.observe(this){
            binding.btnAddFood.isEnabled = !it
        }

        binding.btnAddFood.setOnClickListener {
            postFood()
        }

        observeFood()
    }
    private fun setupCategoryAdapter(){
        categoryAdapter = ArrayAdapter(this, R.layout.input_list_item, category)
        binding.edCategory.setAdapter(categoryAdapter)
    }

    private fun postFood() {
        val foodName = binding.edFoodName.text.toString()
        val category = binding.edCategory.text.toString()
        val carbs = binding.edCarbs.text.toString().toInt() ?: 0
        val protein = binding.edProtein.text.toString().toInt() ?: 0
        val fats = binding.edFats.text.toString().toInt() ?: 0
        val calories = binding.edCalories.text.toString().toInt() ?: 0

        if (foodName.isNotEmpty() && category.isNotEmpty()) {
            viewModel.postNewFood( foodName, category, carbs, protein, fats, calories)
        } else {
            Snackbar.make(binding.root, "Please fill out all fields", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun observeFood() {
        viewModel.newFood.observe(this ) {
            val intent = Intent(this, FoodDetailActivity::class.java).apply {
                putExtra("foodName", it.foodName)
                putExtra("category", it.category)
                putExtra("carbs", it.carbs as Double)
                putExtra("protein", it.proteins)
                putExtra("fats", it.fats)
                putExtra("calories", it.calories)
                putExtra("gIndex", it.giValue as Int)
                putExtra("gLoad", it.glValue as Double)
            }
            startActivity(intent)
        }
    }
}