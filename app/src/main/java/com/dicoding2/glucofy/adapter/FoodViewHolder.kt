package com.dicoding2.glucofy.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.databinding.ItemFoodBinding

class FoodViewHolder (val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(food: FoodListItem) {
        binding.tvFoodName.text = food.name
        binding.tvCalories.text = food.caloriesKcal.toString()
        binding.tvFats.text = food.fatsG.toString()
        binding.tvProtein.text = food.proteinsG.toString()
        binding.tvGlIndex.text = food.carbohydratesG.toString()
    }
}
