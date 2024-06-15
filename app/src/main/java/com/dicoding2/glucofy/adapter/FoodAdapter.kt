package com.dicoding2.glucofy.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.databinding.ItemFoodBinding
import com.dicoding2.glucofy.model.Food
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonDisposableHandle.parent

class FoodAdapter : PagingDataAdapter<FoodListItem, FoodViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)

        if (food!= null){
            holder.bind(food)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }
}