package com.dicoding2.glucofy.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.databinding.ItemFoodBinding

class FoodViewHolder (val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(food: FoodListItem) {
        binding.tvFoodName.text = food.foodName
        binding.tvCalories.text = food.calories.toString()
        binding.tvFats.text = food.fats.toString()
        binding.tvProtein.text = food.proteins.toString()
        binding.tvGlIndex.text = food.gIndex.toString()
    }
}
