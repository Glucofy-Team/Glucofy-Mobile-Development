package com.dicoding2.glucofy.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.databinding.ItemFoodBinding
import com.dicoding2.glucofy.ui.food.FoodDetailActivity

class FoodAdapter : PagingDataAdapter<FoodListItem, FoodAdapter.FoodViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)
        if (food != null) {
            holder.bind(food)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, FoodDetailActivity::class.java)
                intent.putExtra("id", food.id)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FoodListItem>() {
            override fun areItemsTheSame(oldItem: FoodListItem, newItem: FoodListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FoodListItem, newItem: FoodListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class FoodViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FoodListItem) {
            binding.tvFoodName.text = food.foodName
            binding.tvCalories.text = food.calories.toString()
            binding.tvFats.text = food.fats.toString()
            binding.tvProtein.text = food.proteins.toString()
            binding.tvGlIndex.text = food.gIndex.toString()
        }
    }
}
