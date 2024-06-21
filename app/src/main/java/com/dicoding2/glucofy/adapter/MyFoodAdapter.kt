package com.dicoding2.glucofy.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.data.remote.response.MyFoodListItem
import com.dicoding2.glucofy.databinding.ItemFoodBinding
import com.dicoding2.glucofy.ui.food.MyFoodDetailActivity

class MyFoodAdapter : ListAdapter<MyFoodListItem, MyFoodAdapter.MyFoodViewHolder> (DIFF_CALLBACK) {
    class MyFoodViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: MyFoodListItem) {
            binding.tvFoodName.text = food.foodName
            binding.tvCalories.text = "${food.calories.toString()} kkal"
            binding.tvGlIndex.text = food.gIndex.toString()

            when {
                food.gIndex!! <= 55 -> {
                    binding.circleLayout.setBackgroundResource(R.drawable.circle_background_low)
                }
                food.gIndex <= 69 -> {
                    binding.circleLayout.setBackgroundResource(R.drawable.circle_background_medium)
                }
                else -> {
                    binding.circleLayout.setBackgroundResource(R.drawable.circle_background_high)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyFoodViewHolder, position: Int) {
        val food = getItem(position)
        if (food != null) {
            holder.bind(food)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, MyFoodDetailActivity::class.java).apply {
                    putExtra("foodId", food.id)
                    putExtra("foodName", food.foodName)
                    putExtra("calories", food.calories)
                    putExtra("fats", food.fats as Double)
                    putExtra("proteins", food.proteins as Double)
                    putExtra("carbs", food.carbs as Double)
                    putExtra("gIndex", food.gIndex)
                    putExtra("gLoad", food.gLoad as Double)
                    putExtra("category", food.category)
                }
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyFoodViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MyFoodListItem>() {
            override fun areItemsTheSame(oldItem: MyFoodListItem, newItem: MyFoodListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MyFoodListItem, newItem: MyFoodListItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}