package com.dicoding2.glucofy.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.databinding.ItemFoodBinding
import com.dicoding2.glucofy.model.Food
import com.dicoding2.glucofy.ui.FoodDetailActivity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonDisposableHandle.parent

class FoodAdapter : PagingDataAdapter<FoodListItem, FoodViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)

        if (food!= null){
            holder.bind(food)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, FoodDetailActivity::class.java)
            if (food != null) {
                intent.putExtra("id", food.id)
            }

            holder.itemView.context.startActivity(intent)
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
}