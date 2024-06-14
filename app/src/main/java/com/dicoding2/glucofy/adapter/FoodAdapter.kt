package com.dicoding2.glucofy.adapter

import androidx.paging.PagingDataAdapter
import com.dicoding2.glucofy.data.remote.response.FoodListItem

class FoodAdapter : PagingDataAdapter<FoodListItem, FoodAdapter.FoodViewHolder>(DIFF_CALLBACK) {
}